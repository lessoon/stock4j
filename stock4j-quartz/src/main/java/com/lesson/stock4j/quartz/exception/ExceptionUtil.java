package com.lesson.stock4j.quartz.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误信息处理类
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.8
 * <p>Date: 2020-07-21 11-30
 */
public class ExceptionUtil {
    /**
     * 获取exception的详细错误信息。
     */
    public static String getExceptionMessage(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        return str;
    }

    public static String getRootErrorMseeage(Exception e) {
        Throwable root = ExceptionUtils.getRootCause(e);
        root = (root == null ? e : root);
        if (root == null) {
            return "";
        }
        String msg = root.getMessage();
        if (msg == null) {
            return "null";
        }
        return StringUtils.defaultString(msg);
    }
}
