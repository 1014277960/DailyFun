package com.wulingpeng.havefun.ui;

import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.wulingpeng.havefun.R;

import butterknife.BindView;

/**
 * Created by wulinpeng on 16/8/13.
 */
public class SettingActivity extends BaseActivity {
    @Override
    protected void initLayoutId() {
        layoutId = R.layout.activity_setting;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
