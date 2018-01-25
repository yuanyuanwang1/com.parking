package com.wy.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author Liuyang
 * @version 1.0
 */
public class MathUtil {
	private MathUtil() {
	}

	private static BigDecimal createBigDecimal(double d) {
		return new BigDecimal(Double.toString(d));
	}

	/**
	 * 精确加法运算
	 */
	public static double add(double d1, double d2) {
		return createBigDecimal(d1).add(createBigDecimal(d2),
				MathContext.DECIMAL64).doubleValue();
	}

	/**
	 * 精确减法运算
	 */
	public static double subtract(double d1, double d2) {
		return createBigDecimal(d1).subtract(createBigDecimal(d2),
				MathContext.DECIMAL64).doubleValue();
	}

	/**
	 * 精确乘法运算
	 */
	public static double multiply(double d1, double d2) {
		return createBigDecimal(d1).multiply(createBigDecimal(d2),
				MathContext.DECIMAL64).doubleValue();
	}

	/**
	 * 精确除法运算
	 */
	public static double divide(double d1, double d2) {
		return createBigDecimal(d1).divide(createBigDecimal(d2),
				MathContext.DECIMAL64).doubleValue();
	}

	/**
	 * 精确除法运算，保留若干位小数
	 */
	public static double divide(double d1, double d2, int newScale) {
		return createBigDecimal(d1).divide(createBigDecimal(d2), newScale,
				RoundingMode.HALF_UP).doubleValue();
	}

	public static int compare(double d1, double d2) {
		return createBigDecimal(d1).compareTo(createBigDecimal(d2));
	}

	public static double scale(double d, int newScale) {
		return createBigDecimal(d).setScale(newScale, RoundingMode.HALF_UP)
				.doubleValue();
	}

	public static Double scale(BigDecimal b, int newScale) {

		if (b == null) {

			return null;

		}

		return b.setScale(newScale, RoundingMode.HALF_UP).doubleValue();
	}

	public static double liangWei(Double d) {

		d = (double) Math.round(d * 100) / 100;

		return d;

	}

	public static BigDecimal getPercent(BigDecimal number) {

		return number.divide(new BigDecimal(100), 2);

	}

	public static void main(String[] args) {
		// double d1 = 5.46353;
		// double d2 = 0.17;
		// System.out.println(d1 - d2);
		// System.out.println(MathUtil.subtract(d1, d2));

		BigDecimal a = new BigDecimal(100.030505064);

		System.out.print(scale(a, 2));

	}
}
