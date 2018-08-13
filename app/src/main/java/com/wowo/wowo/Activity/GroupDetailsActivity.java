package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.DeleteDialog;
import com.wowo.wowo.Views.Dialog.GroupCodeDialog;

public class GroupDetailsActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvCode,mTvExit;
    private CheckBox mCbShield;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupdeatails);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvCode=findView(R.id.group_tv_code);
        mTvExit=findView(R.id.group_tv_exit);
    }
    private void init() {
        bundle = getIntent().getExtras();
        mTvTitle.setText("社群详情");
    }
    private void initListener() {
        mTvCode.setOnClickListener(this);
        mTvExit.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.group_tv_code:
                showCodeDialog("wowo官方群","01010");
                break;
            case R.id.group_tv_exit:
                showDeleteDialog();
                break;
        }
    }
    private void showDeleteDialog() {
            DeleteDialog.Builder dialog = new DeleteDialog.Builder(this);
            dialog.create("退出社群","确定删除该社群和相关数据").show();
            dialog.setOnSureClickListener(new DeleteDialog.Builder.OnSureClickListener() {
                @Override
                public void onClick() {

                }
            });
    }
    private void showCodeDialog(String name ,String id) {
        GroupCodeDialog.Builder dialog = new GroupCodeDialog.Builder(this);
        dialog.create(name, "0101").show();
    }
}
