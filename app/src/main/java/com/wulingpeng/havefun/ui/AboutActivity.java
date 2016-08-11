package com.wulingpeng.havefun.ui;

import android.view.MenuItem;

import com.wulingpeng.havefun.R;

/**
 * Created by wulinpeng on 16/8/2.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void initLayoutId() {
        layoutId = R.layout.activity_about;
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
