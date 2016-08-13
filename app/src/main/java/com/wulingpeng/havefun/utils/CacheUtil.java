package com.wulingpeng.havefun.utils;

import android.app.Application;

import java.io.File;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class CacheUtil {

    //
    public static String getCacheSize(File file) {
        long kb = getFolderSize(file) / 1024;
        long mb = kb / 1024;
        if (mb < 1) {
            return String.valueOf(kb) + "KB";
        }
        long gb = mb / 1024;
        if (gb < 1) {
            return mb + "MB " + kb % 1024 + "KB";
        }
        return gb + "GB " + mb % 1024 + "MB";
    }

    private static long getFolderSize(File file) {
        long size = 0;
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                size += getFolderSize(f);
            } else {
                size += f.length();
            }
        }
        return size;
    }
}
