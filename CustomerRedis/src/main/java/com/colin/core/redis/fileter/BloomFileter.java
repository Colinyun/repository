package com.colin.core.redis.fileter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @auth c-chenyun
 * @date 2020-06-30
 * @describe  布隆拦截器
 * 布隆拦截器的原理是，通过bit数组，把字符串散列或去hash值，并在bit数据的指定位置上把0修改为1，
 * 当判断值是否存在是，就是拿到字符串散列hash函数计算的index位上的值是否都未1，如果是，则说明存在，当然是否存在有误判的可能性，
 * 如果不是，那一定说明不存在该值。
 * 此算法适合过去大多数据，在不考虑误判率的情况下，布隆过滤器有着时间和空间上的优势，
 * 如1B = 8bit,1kb = 1024B,
 * 假使：一个网站有100亿个URL，一个url长度位64B是，如果用HashMap来实现则需要64GB的空间，一般服务器很难达到这个要求，
 * 我们可以通过布隆过滤来实现，通过布隆拦截器仅仅需要约23GB的空间范围，当然我们还可以根据数据切割来让空间更小的过滤
 *
 */
public class BloomFileter implements Serializable {

	private static final long serialVersionUID = 8954400904965047441L;

	private final int[] seeds;

	private final int size;

	private final BitSet notebook;

	private final MisjudgmentRate rate;

	private final AtomicInteger userCount = new AtomicInteger(0);

	private final Double autoClearRate;

	/**
	 * 默认中等程序的误判率，MisjudgmentRate.MIDDLE 以及不自动清空数据（性能会有少许提升）
	 * 
	 * @param dataCount 预期处理的数据规模，如预期用于处理100万数据的查重，这里则填写1000000
	 * 
	 */
	public BloomFileter(int dataCount) {
		this(MisjudgmentRate.MIDDLE, dataCount, null);
	};

	/**
	 * 
	 * @param rate			一个枚举类型的误判率
	 * @param dataCount		 预期处理的数据规模，如预期用于处理100万数据的查重，这里则填写1000000
	 * @param autoClearRate	
	 * 						自动清空过滤器内部信息的使用比率，传null则表示不会自动清理
	 * 						当过滤器使用率达到100%时，则无论传入什么数据，都会认为该数据已经存在了
	 * 						当希望过滤器使用率达到80%时，自动清空重新使用，则传入0.8
	 */
	public BloomFileter(MisjudgmentRate rate, int dataCount, Double autoClearRate) {
		long bitSize = rate.seeds.length * dataCount;
		if (bitSize < 0 || bitSize > Integer.MAX_VALUE) {
			throw new RuntimeException("位数太大溢出了，请降低误判率或降低数据大小...");
		}
		this.rate = rate;
		this.seeds = rate.seeds;
		this.size = (int) bitSize;
		this.notebook = new BitSet(this.size);
		this.autoClearRate = autoClearRate;
	}

	public void add(String data) {
		checkNeedClear();

		for (int i = 0, num = seeds.length; i < num; i++) {
			int index = hash(data, seeds[i]);
			setTrue(index);
		}
	}

	public boolean check(String data) {
		for (int i = 0, num = seeds.length; i < num; i++) {
			int hash = hash(data, seeds[i]);
			if (!notebook.get(hash)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-30
	 * @describe 如果不存在就进行记录并返回false，如果存在了就返回true  
	 *
	 */
	public boolean addIfNotExist(String data) {
		checkNeedClear();
		int[] indexs = new int[seeds.length];
		boolean isExist = true;
		int index;
		for (int i = 0, num = seeds.length; i < num; i++) {
			indexs[i] = index = hash(data, seeds[i]);
			if (isExist && !notebook.get(index)) {
				isExist = false;
				for (int j = 0; j <= i; j++) {
					setTrue(indexs[j]);
				}
			}

			if (!isExist) {
				setTrue(index);
			}
		}
		return isExist;
	}

	private void setTrue(int index) {
		userCount.incrementAndGet();
		notebook.set(index, true);
	}

	private int hash(String data, int seeds) {
		char[] array = data.toCharArray();
		int hash = 0;
		if (array.length > 0) {
			for (int i = 0, num = array.length; i < num; i++) {
				hash = i * hash + array[i];
			}
		}
		hash = hash * seeds % size;
		return Math.abs(hash);
	}

	private void checkNeedClear() {
		if (autoClearRate != null) {
			if (getUserRate() >= autoClearRate) {
				synchronized (this) {
					if (getUserRate() >= autoClearRate) {
						clear();
					}
				}
			}
		}

	}

	public double getUserRate() {
		return (double) userCount.intValue() / (double) size;
	}

	public void saveFilterToFile(String path) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static BloomFileter readFilterFormFile(String path) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
			return (BloomFileter) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void clear() {
		userCount.set(0);
		notebook.clear();
	}

	public MisjudgmentRate getRate() {
		return rate;
	}

	/**
	 * 
	 * @auth c-chenyun
	 * @date 2020-06-30
	 * @describe  布隆过滤器的误判率枚举类
	 * 分配的位数越多，误判率越低但越占内存
	 * 
	 * 4个位的误判率大概是：0.14689159766308
	 *
	 * 8个位的误判率大概是：0.02157714146322
	 * 
	 * 16个位的误判率大概是：0.00046557303372
	 * 
	 * 32个位的误判率大概是：0.00000021167340
	 */
	public enum MisjudgmentRate {

		/**
		 * 每个字符串分配4个位
		 */
		VERY_SMALL(new int[] { 2, 3, 5, 7 }),

		/**
		 * 每个字符串分配8个位
		 */
		SMALL(new int[] { 2, 3, 5, 7, 11, 13, 17, 19 }),

		/**
		 * 每个字符串分配16位
		 */
		MIDDLE(new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53 }),

		/**
		 * 每个字符串分配32个位
		 */
		HIGH(new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97,
				101, 103, 107, 109, 113, 127, 131 });

		private int[] seeds;

		private MisjudgmentRate(int[] seeds) {
			this.seeds = seeds;
		}

		public int[] getSeeds() {
			return seeds;
		}

		public void setSeeds(int[] seeds) {
			this.seeds = seeds;
		}

	}
}
