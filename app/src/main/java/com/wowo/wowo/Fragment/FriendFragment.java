package com.wowo.wowo.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wowo.wowo.Activity.FriendMessageActivity;
import com.wowo.wowo.Activity.GroupMessageActivity;
import com.wowo.wowo.Activity.SearchActivity;
import com.wowo.wowo.Adapter.GroupRcAdapter;
import com.wowo.wowo.Base.BaseFragment;
import com.wowo.wowo.Model.GroupHotInfoModel;
import com.wowo.wowo.Model.UserModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

//import com.wowo.wowo.rxjava.BaseResponse;
//import com.wowo.wowo.rxjava.ProgressSubscriber;
//import com.wowo.wowo.rxjava.RetrofitAPIManager;
//import com.wowo.wowo.rxjava.SubscriberOnNextListener;


/**
 */

public class FriendFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvTitle,mTvFriend,mtvGroup;
    private ImageView mIvSea,mIvAdd;
    private RecyclerView mRecyclerView;
    private GroupRcAdapter mAdapter;
//    private SelectCountModel models;
    private UserModel model;
    private Bundle bundle;
    private List<GroupHotInfoModel> models;
    @Override
    protected void instanceRootView(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.fragment_friends, null);
    }

    @Override
    protected void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvFriend =findView( R.id.friend_tv_friends);
        mtvGroup=findView(R.id.friend_tv_group);
        mRecyclerView = findView(R.id.frafriend_rc_group);
        mIvSea = findView(R.id.title_layout_iv_sea);
        mIvAdd = findView(R.id.title_layout_iv_add);

    }

    @Override
    protected void initListener() {
        mTvFriend.setOnClickListener(this);
        mtvGroup.setOnClickListener(this);
        mIvSea.setOnClickListener(this);
        mIvAdd.setOnClickListener(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTvTitle.setText("朋友");
        model = SharedPreferencesManager.getInstance().getUserData(getActivity());
        bundle = new Bundle();
        bundle.putString("userId", model.getUserId());
        bundle.putString("type", "0");
        initData();
    }
    private void initData() {
        List<String> list = new ArrayList<>();
        for(int i = 0; i<30; i++){
            list.add(i+"群组");
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new GroupRcAdapter(getActivity(),list);
        mRecyclerView.setAdapter(mAdapter);
//        SubscriberOnNextListener mListener = new SubscriberOnNextListener<BaseResponse<SelectCountModel>>() {
//            @Override
//            public void onNext(BaseResponse<SelectCountModel> baseResponse) {
//                models = baseResponse.data;
//                if (models != null) {
//                    mTvSkillNum.setText(models.getServiceNum() + "");
//                    mTvRequestNum.setText(models.getDemandNum() + "");
//                    mTvCollectionNum.setText(models.getCollectNum() + "");
//                    mTvOrderNum.setText(models.getOrderNum() + "");
//                    mTvName.setText(models.getUserNick());
//                    if (!models.getVipExpireTime().equals("1970/01/01")) {
//                        mTvVipTime.setText(models.getVipExpireTime());
//                    }
//                    switch (models.getUserGender()) {//判断用户性别
//                        case 0:
//                            mIvGender.setImageResource(R.mipmap.profile_icon_n_nor);
//                            break;
//                        case 1:
//                            mIvGender.setImageResource(R.mipmap.profile_icon_nan_nor);
//                            break;
//                    }
//                    if (models.getDefaultAvatar() != null) {
//                        GlideUtils.loadImageViewForHead(getActivity(), models.getDefaultAvatar().get(0).getUrl(), mIvHead);
//                    }
//                    switch (models.getIsIdentity()) {
//                        case 0:
//                            mAuthentication.setEnabled(true);//未认证
//                            mTvIdentity.setText("未认证");
//                            break;
//                        case 1:
//                            mAuthentication.setEnabled(false);//已认证
//                            mTvIdentity.setText("已认证");
//                            break;
//                        case 2:
//                            mAuthentication.setEnabled(false);//审核中
//                            mTvIdentity.setText("审核中");
//                            break;
//                    }
//                }
//            }
//        };
//        RetrofitAPIManager.getInstance().selectCount(new ProgressSubscriber<BaseResponse<SelectCountModel>>(mListener, getActivity(), 0), Encrypt.base64(model.getUserId()));

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_layout_iv_sea :
                bundle.putString("name", "1");
                openActivity(SearchActivity.class, bundle);
                break ;
            case R.id.title_layout_iv_add :

                break ;
            case R.id.friend_tv_friends://好友
//                bundle.putString("head", models.getDefaultAvatar().get(0).getUrl());
//                bundle.putString("sex", models.getUserGender() + "");
                bundle.putString("name", "1");
                openActivity(FriendMessageActivity.class, bundle);
                break;
            case R.id.friend_tv_group:
                openActivity(GroupMessageActivity.class, bundle);
                break;

        }
    }
}
