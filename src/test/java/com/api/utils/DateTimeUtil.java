package com.api.utils;

import org.joda.time.DateTime;

public class DateTimeUtil {

    private DateTimeUtil() {}
    
    public static String getTimeWithDaysAgo(int days) {
        return DateTime.now().minusDays(days).toInstant().toString();
    }
}