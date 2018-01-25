package com.wy.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Liuyang
 * @version 1.2
 */
public class StringUtil {

	public static String uppercase2underscore(String str) {

		str = StringUtils.replace(str, "A", "_a");

		str = StringUtils.replace(str, "B", "_b");

		str = StringUtils.replace(str, "C", "_c");

		str = StringUtils.replace(str, "D", "_d");

		str = StringUtils.replace(str, "E", "_e");

		str = StringUtils.replace(str, "F", "_f");

		str = StringUtils.replace(str, "G", "_g");

		str = StringUtils.replace(str, "H", "_h");

		str = StringUtils.replace(str, "I", "_i");

		str = StringUtils.replace(str, "J", "_j");

		str = StringUtils.replace(str, "K", "_k");

		str = StringUtils.replace(str, "L", "_l");

		str = StringUtils.replace(str, "M", "_m");

		str = StringUtils.replace(str, "N", "_n");

		str = StringUtils.replace(str, "O", "_o");

		str = StringUtils.replace(str, "P", "_p");

		str = StringUtils.replace(str, "Q", "_q");

		str = StringUtils.replace(str, "R", "_r");

		str = StringUtils.replace(str, "S", "_s");

		str = StringUtils.replace(str, "T", "_t");

		str = StringUtils.replace(str, "U", "_u");

		str = StringUtils.replace(str, "V", "_v");

		str = StringUtils.replace(str, "W", "_w");

		str = StringUtils.replace(str, "X", "_x");

		str = StringUtils.replace(str, "Y", "_y");

		str = StringUtils.replace(str, "Z", "_z");

		return str;

	}

	public static void main(String[] args) {

		String str = "costTotal decimal(32, 2),  -- 成本总计	zeroCostTotal decimal(32, 2),  -- 零成本总计	costPer decimal(32, 2),  -- 单只总成本	profitsPer decimal(32, 2),  -- 单只利润总额	retainedProfit decimal(32, 2),  -- 净利润	retainedProfitRate";

		System.out.println(uppercase2underscore(str));

	}

}
