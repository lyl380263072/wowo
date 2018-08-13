package com.wowo.wowo.Views.PhotoPreview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;

import java.util.ArrayList;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2018/1/19 0019
 * desc  :预览头像
 */
public class PhotoPreActivity extends BaseActivity implements ViewPager.OnPageChangeListener{
    private RelativeLayout mTitleBar;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvIndicator;
    private ViewPager mVpPager;
    private PhotoPreAdapter adapter;
    private ArrayList<String> mPhotoList;
    private Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        findView();
        initListener();
        bundle = getIntent().getExtras();
        mPhotoList = bundle.getStringArrayList("photolist");
        adapter = new PhotoPreAdapter(this,mPhotoList);
        mVpPager.setAdapter(adapter);
    }

    private void initListener() {
        mVpPager.addOnPageChangeListener(this);
    }

    private void findView() {
        mTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvIndicator = (TextView) findViewById(R.id.tv_indicator);
        mVpPager = (ViewPager) findViewById(R.id.vp_pager);
    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTvIndicator.setText((position + 1) + "/" + mPhotoList.size());
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
