package com.wowo.wowo.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 各种验证
 * Created by andy on 2016/5/30.
 */

public class CheckUtils {

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 电子邮件验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isEmail(String str) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(str);
        //Mather m = p.matcher("wangxu198709@gmail.com.cn");这种也是可以的！
        boolean b = m.matches();
        return b;
    }


    /**
     * 是否整形
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isInt(String str) {
        boolean b = false;
        try {
            int i = Integer.parseInt(str);
            b = true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    /**
     * 是否浮点型
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isDouble(String str) {
        boolean b = false;
        try {
            double i = Double.parseDouble(str);
            b = true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }

    /**
     * 首字母转小写
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 首字母转大写
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 字符串转字符数组，去掉空格，回车
     *
     * @param str
     * @return
     */
    public static char[] stringToArr(String str) {
        str = str.replace(" ", "");
        str = str.replace("\n", "");
        return str.toCharArray();
    }

    /**
     * 用户名格式
     *
     * @param str
     * @return
     */
    public static boolean userName(String str) {
        Pattern p1 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[A-Za-z0-9]{6,20}$");
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 密码格式
     *
     * @param str
     * @return
     */
    public static boolean password(String str) {
        Pattern p1 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("(?!^(\\d+|[a-zA-Z]+|[_]+)$)^[\\w_\\_]+$");
        if (str.length() >= 6) {
            m = p1.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 中文检测
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        Pattern p1 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[\u4e00-\u9fa5]{4,8}$");
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        }
        return b;
    }


    /**
     * 身份证检测
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        Pattern p1 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X)?$");
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        }
        return b;
    }

}
