/**
 * 
 */
package com.wy.util;

/**
 * @author Administrator
 * 
 */
public class ExceptionUtil {

	public static String getException(Throwable t) {

		if (t == null) {

			return null;

		}

		StackTraceElement[] ste = t.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(t.getMessage() + "");
		for (int i = 0; i < ste.length; i++) {
			sb.append(ste[i].toString() + "");
		}

		return sb.toString();

	}

}
