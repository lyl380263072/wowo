package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.BottomDialog;
import com.wowo.wowo.Views.Dialog.DeleteDialog;

public class FriendsDetailsActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvAction;
    private Button mBtRemake,mBtDeleFriend;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frienddeatails);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvAction = findView(R.id.title_layout_tv_action);
        mBtRemake= findView(R.id.frdet_bt_remake);
        mBtDeleFriend= findView(R.id.frdet_bt_delete);
    }
    private void init() {
        bundle = getIntent().getExtras();
        mTvTitle.setText("好友详情");
    }
    private void initListener() {
        mBtRemake.setOnClickListener(this);
        mBtDeleFriend.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.frdet_bt_remake:
                openActivity(RemarkEditActivity.class);
                break;
            case R.id.frdet_bt_delete:
                showDeleteDialog();
                break;
        }
    }
    private void showDeleteDialog() {
        DeleteDialog.Builder dialog = new DeleteDialog.Builder(this);
        dialog.create("删除好友","确定删除该好友和相关数据").show();
        dialog.setOnSureClickListener(new DeleteDialog.Builder.OnSureClickListener() {
            @Override
            public void onClick() {

            }
        });
    }

}
