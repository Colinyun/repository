package com.colin.core.redis.test.fileter;

import org.junit.Test;

import com.colin.core.redis.fileter.BloomFileter;

public class BloomFileterTest {

	@Test
	public void bloomFileterTest() {
		BloomFileter fileter = new BloomFileter(7);
		StringBuilder sb = new StringBuilder("是否包含{123456}:").append(fileter.addIfNotExist("123456")).append("\n");
		sb.append("是否包含{22222222222222222}:").append(fileter.addIfNotExist("22222222222222222")).append("\n");
		sb.append("是否包含{33333333333333333}:").append(fileter.addIfNotExist("33333333333333333")).append("\n");
		sb.append("是否包含{44444444444444444}:").append(fileter.addIfNotExist("44444444444444444")).append("\n");
		sb.append("是否包含{55555555555555555}:").append(fileter.addIfNotExist("55555555555555555")).append("\n");
		sb.append("是否包含{66666666666666666}:").append(fileter.addIfNotExist("66666666666666666")).append("\n");
		sb.append("是否包含{123456}:").append(fileter.addIfNotExist("123456")).append("\n");
		fileter.saveFilterToFile("G:\\桌面\\obj.obj");
		fileter = BloomFileter.readFilterFormFile("G:\\\\桌面\\\\obj.obj");
		sb.append(fileter.getUserRate()).append("\n");
		sb.append("是否包含{22222222222222222}:").append(fileter.addIfNotExist("22222222222222222")).append("\n");
		System.out.println(sb);
	}
}
