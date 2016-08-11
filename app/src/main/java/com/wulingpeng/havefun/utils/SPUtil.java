package com.wulingpeng.havefun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wulingpeng.havefun.DailyFun;

/**
 * Created by wulinpeng on 16/8/2.
 */
public class SPUtil {

    public static Context context = DailyFun.context;

    public static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    public static SharedPreferences.Editor editor = sp.edit();

    public static String getString(String s) {
        return sp.getString(s, "");
    }

    public static void save(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
}
