package com.github.chm.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenhuaming on 16/7/10.
 */
public class DateUtil {
    public static String format(Date date, String pattern) {

        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    public static String format(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return df.format(date);
    }
}
