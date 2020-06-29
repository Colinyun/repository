package com.colin.rabbit.mq.commons;

public class SysConstants {

	/************************** topic *****************************/
	public final static String COLIN_TOPIC_EXHANGE = "colin.topic.exchange";

	public final static String COLIN_TOPIC_QUEUE = "colin.topic.queue";

	public final static String COLIN_TOPIC_ROUTE_REGIX = "colin.topic.#";

	public final static String COLIN_TOPIC_ROUTE_KEY = "colin.topic.key";

	/************************** 死信队列 *****************************/
	public final static String COLIN_DEAD_LETTER_EXCHANGE = "colin.dead.letter.exchange";

	public final static String COLIN_DEAD_LETTER_QUEUE = "colin.dead.letter.queue";

	public final static String COLIN_DEAD_LETTER_ROUTE_KEY = "colin.dead.letter.key";

	public final static String COLIN_DEAD_LETTER_ROUTE_REGIX = "colin.dead.letter.*";

	public final static String COLIN_DEAD_REDIRECT_QUEUE = "colin.dead.redirect.queue";

	public final static String COLIN_DEAD_REDIRECT_ROUTE_KEY = "colin.dead.redirect.route.key";

	public final static String COLIN_DEAD_REDIRECT_ROUTE_REGIX = "colin.dead.redirect.route.*";

	/************************** 延迟队列 *****************************/
	public final static String COLIN_DELAY_EXCHANGE = "colin.delay.exchange";

	public final static String COLIN_DELAY_FORWARDING_EXCHANGE = "colin.delay.forwarding.exchange";

	public final static String COLIN_DELAY_QUEUE = "colin.delay.queue";

	public final static String COLIN_DELAY_FORWARDING_QUEUE = "colin.delay.forwarding.queue";

	public final static String COLIN_DELAY_ROUTE_KEY = "colin.delay.key";

	public final static String COlin_DELAY_FORWARDING_ROUTE_KEY = "colin.delay.forwarding.key";

	public final static String COLIN_DELAY_ROUTE_REGIX = "colin.delay.*";

	public final static String COLIN_DELAY_FORWARDING_ROUTE_REGIX = "colin.delay.forwarding.*";
}
