package com.study.work.common.util;

public class StringUtils {
	
    public static String objectIfNullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String ifNullToEmpty(Object obj) {
        return obj == null ? "" : obj.toString();
    }

}
