package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.BottomDialog;

public class FriendMessageActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvInviten,mTvFlist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendmessage);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvInviten= findView(R.id.friend_tv_inviten);
        mTvFlist= findView(R.id.friend_tv_friendlist);
    }
    private void init() {
        bundle = getIntent().getExtras();
        mTvTitle.setText("好友");
    }
    private void initListener() {
        mTvInviten.setOnClickListener(this);
        mTvFlist.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.friend_tv_inviten:
                showShareDialog();
                break;
            case R.id.friend_tv_friendlist:
                bundle.putString("name", "1");
                openActivity(FriendListActivity.class, bundle);
                break;
        }
    }

    private void showShareDialog() {
        BottomDialog.Builder dialog = new BottomDialog.Builder(this);
        dialog.create("0","x").show();
    }
}
