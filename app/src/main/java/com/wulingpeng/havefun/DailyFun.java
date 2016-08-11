package com.wulingpeng.havefun;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wulinpeng on 16/8/2.
 */
public class DailyFun extends Application {

    // 自定义Application，这个context可以作为得到sp用
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 设置Realm默认配置
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).schemaVersion(0).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
