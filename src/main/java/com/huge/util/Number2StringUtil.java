package com.huge.util;

public class Number2StringUtil {
	public static String number2String(Long n, int length) {
		String str = String.format("%0" + length + "d", n);
		return str;
	}

	public static String number2String(long n, int length) {
		String str = String.format("%0" + length + "d", n);
		return str;
	}

	public static String number2String(Integer n, int length) {
		String str = String.format("%0" + length + "d", n);
		return str;
	}

	public static String number2String(int n, int length) {
		String str = String.format("%0" + length + "d", n);
		return str;
	}
}
