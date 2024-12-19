package com.tabisketch.util;

public class StringUtils {
    public static boolean nullAndEmpty(final String str) {
        return str == null || str.isEmpty();
    }

    public static boolean notNullAndNotEmpty(final String str) {
        return str != null && !str.isEmpty();
    }
}
