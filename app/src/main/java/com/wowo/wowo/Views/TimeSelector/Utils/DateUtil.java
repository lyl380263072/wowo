package com.wowo.wowo.Views.TimeSelector.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuli on 2015/11/27.
 */
public class DateUtil {

    public static final String DEFAULT_TEMPLATE_DAY = "yyyy-MM-dd";
    public static final String DEFAULT_TEMPLATE = "yyyy-MM-dd HH:mm";

    private DateUtil(){}



    /**
     *-日期控件
     * * /
     /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (TextUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     *
     * @param date
     * @return 以 2017/12/1 下午4:00 格式返回
     */
    public static String format(Date date) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat();
            returnValue = df.format(date);
        }
        return (returnValue);
    }

}
