package com.lesson.stock4j.spider.util;

import java.util.UUID;

/**
 * String Tool
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-16 16-25
 */
public class StringUtils {
    public static String get32Uuid(){
        return UUID.randomUUID().toString();
    }

}
