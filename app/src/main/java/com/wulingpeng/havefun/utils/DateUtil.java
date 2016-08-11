package com.wulingpeng.havefun.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wulinpeng on 16/8/2.
 */
public class DateUtil {

    /**
     * 解析时间
     * @param date
     * @return
     */
    public static String parseStandardDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        formatter.setLenient(false);
        return formatter.format(date);
    }
}
