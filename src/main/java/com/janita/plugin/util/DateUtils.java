package com.janita.plugin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DateUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        return DEFAULT_FORMATTER.format(now);
    }
}