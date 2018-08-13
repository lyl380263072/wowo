package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.BottomDialog;

public class GroupMessageActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvNewGroup,mTvGlist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupmessage);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvNewGroup= findView(R.id.group_tv_new);
        mTvGlist= findView(R.id.group_tv_list);
    }
    private void init() {
        bundle = getIntent().getExtras();
        mTvTitle.setText("我的群组");
    }
    private void initListener() {
        mTvNewGroup.setOnClickListener(this);
        mTvGlist.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.group_tv_new://新建群组
                openActivity(NewGroupActivity.class,bundle);
                break;
            case R.id.group_tv_list:
                bundle.putString("name", "1");
                openActivity(FriendListActivity.class, bundle);
                break;
        }
    }
}
