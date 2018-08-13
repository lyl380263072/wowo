package com.wowo.wowo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.wowo.wowo.Adapter.FriendListRcAdapter;
import com.wowo.wowo.Adapter.GroupRcAdapter;
import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.Model.FriendListModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.Dialog.BottomDialog;

import java.util.ArrayList;
import java.util.List;

public class FriendListActivity extends BaseActivity{
    private Bundle bundle;
    private TextView mTvTitle,mTvAction,mTvCancel;
    private RecyclerView mRcFrList;
    FriendListRcAdapter  mAdapter ;
    List<FriendListModel> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvAction = findView(R.id.title_layout_tv_action);
        mTvCancel = findView(R.id.flist_tv_cancel);
        mRcFrList = findView(R.id.friend_rc_list);
        mTvAction.setText("完成");
    }
    private void init() {
        bundle = getIntent().getExtras();
        mTvTitle.setText("");
        list = new ArrayList<>();
        for(int i = 0; i<30; i++){
            FriendListModel model = new FriendListModel();
            model.setName(i+"好友");
            list.add(model);
        }
        mRcFrList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new FriendListRcAdapter(this,list,0);
        mRcFrList.setAdapter(mAdapter);
    }
    private void initListener() {
        mTvAction.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

    }
   private int X;
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.title_layout_tv_action://完成
                for(int i = 0; i<list.size(); i++){
                    if (list.get(i).isIscheck()==true){
                      X++;
                    }
                }
                ToastUtils.showShortToast(this,"选择了"+X+"个好友");
                X=0;
                break;
            case R.id.flist_tv_cancel://取消
                for (int i = 0 ;i<list.size();i++){
                    list.get(i).setIscheck(false);
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
    }


}
