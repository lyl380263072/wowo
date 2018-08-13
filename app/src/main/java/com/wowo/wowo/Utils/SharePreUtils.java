package com.wowo.wowo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.prefs.Preferences;

public class SharePreUtils {

    private static SharePreUtils spUtils;
    private static final String preferencesName = "config";
    private SharedPreferences sp;

    private SharePreUtils(Context context) {
        sp = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
    }

    public static SharePreUtils singleton(Context context) {
        synchronized (Preferences.class) {
            if (spUtils == null) {
                synchronized (Preferences.class) {
                    spUtils = new SharePreUtils(context);
                }
            }
        }
        return spUtils;
    }

    public SharedPreferences.Editor edit() {
        return sp.edit();
    }

    public SharedPreferences read() {
        return sp;
    }

}