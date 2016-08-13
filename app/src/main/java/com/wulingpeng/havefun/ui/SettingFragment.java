package com.wulingpeng.havefun.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.wulingpeng.havefun.R;
import com.wulingpeng.havefun.utils.CacheUtil;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    private Preference clearCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_preference);
        clearCache = findPreference("clearCache");
        clearCache.setSummary("Current cache is " + CacheUtil.getCacheSize(getActivity().getApplicationContext().getCacheDir()));
        clearCache.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "clearCache":
                // 打开app设置页面，手动清除cache
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getActivity().getApplicationContext().getPackageName()));
                getActivity().startActivity(i);
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 更新cache
        clearCache.setSummary("Current cache is " + CacheUtil.getCacheSize(getActivity().getApplicationContext().getCacheDir()));
    }
}
