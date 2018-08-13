package com.wowo.wowo.Utils;

/**
 * Created by ZhangXiaoWei
 * 2018/4/3 0003 上午 10:21
 */

public class Utility {
    private static long lastClickTime;
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        return  false;
    }
}
