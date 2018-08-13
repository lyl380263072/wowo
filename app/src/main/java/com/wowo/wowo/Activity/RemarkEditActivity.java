package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.DeleteDialog;

public class RemarkEditActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvAction;
    private EditText mEtRemark;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarkedit);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvAction = findView(R.id.title_layout_tv_action);
        mEtRemark= findView(R.id.remark_edit_et_remark);
    }
    private void init() {
        bundle = getIntent().getExtras();
//        mEtRemark.setText(bundle.get("name").toString());
        mTvTitle.setText("备注编辑");
        mTvAction.setText("保存");
    }
    private void initListener() {
        mTvAction.setOnClickListener(this);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.title_layout_tv_action:

                finish();
                break;
            case R.id.frdet_bt_delete:

                break;
        }
    }

}
