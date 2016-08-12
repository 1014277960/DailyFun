package com.wulingpeng.havefun.utils;

import com.wulingpeng.havefun.mvp.model.Douban;
import com.wulingpeng.havefun.mvp.model.Gank;
import com.wulingpeng.havefun.mvp.model.Zhihu;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by wulinpeng on 16/8/10.
 */
public class DBUtil {

    public static void saveGanks(List<Gank> data, Realm realm) {
        realm.beginTransaction();
        for (Gank gank : data) {
            realm.copyToRealmOrUpdate(gank);
        }
        realm.commitTransaction();
    }

    public static void getGanks(List<Gank> data, Realm realm) {
        data.clear();
        // 结果根据published时间降序
        RealmResults<Gank> results = realm.where(Gank.class).findAllSorted("publishedAt", Sort.DESCENDING);
        data.addAll(results);
    }

    // breast
    public static void saveDoubans(List<Douban> data, Realm realm) {
        realm.beginTransaction();
        for (Douban douban : data) {
            realm.copyToRealmOrUpdate(douban);
        }
        realm.commitTransaction();
    }

    public static void getDoubans(List<Douban> data, Realm realm, int type) {
        data.clear();
        // 结果根据published时间降序
        RealmResults<Douban> results = realm.where(Douban.class).equalTo("type", type).findAllSorted("id", Sort.DESCENDING);
        //RealmResults<Douban> results = realm.where(Douban.class).findAllSorted("id", Sort.DESCENDING);
        data.addAll(results);
    }

    public static void saveZhihus(Realm realm, List<Zhihu> data) {
        realm.beginTransaction();
        for (Zhihu zhihu : data) {
            realm.copyToRealmOrUpdate(zhihu);
        }
        realm.commitTransaction();
    }

    public static RealmResults<Zhihu> getZhihus(Realm realm, String date) {
        RealmResults<Zhihu> results = realm.where(Zhihu.class).equalTo("date", date).findAllSorted("id", Sort.DESCENDING);
        return results;
    }

    public static void deleteZhihus(Realm realm) {
        realm.beginTransaction();
        RealmResults<Zhihu> results = realm.where(Zhihu.class).findAll();
        for (int i = 0; i != results.size(); i++) {
            results.remove(0);
        }
        realm.commitTransaction();
    }

}
