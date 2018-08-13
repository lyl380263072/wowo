package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
/**
 *
 * 搜索页
**/
public class SearchActivity extends BaseActivity{
    private TextView mTvTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        findViews();
        init();
        initListener();
    }
    public void findViews() {

    }
    private void init() {
    }
    private void initListener() {

    }
    @Override
    public void widgetClick(View v) {

    }
}
