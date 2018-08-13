package com.wowo.wowo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseFragment;
import com.wowo.wowo.Model.UserModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.SharedPreferencesManager;

import java.util.ArrayList;

//import com.wowo.wowo.rxjava.BaseResponse;
//import com.wowo.wowo.rxjava.ProgressSubscriber;
//import com.wowo.wowo.rxjava.RetrofitAPIManager;
//import com.wowo.wowo.rxjava.SubscriberOnNextListener;



public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvSkillNum, mTvRequestNum, mTvCollectionNum, mTvOrderNum, mTvTitle, mTvName, mTvVipTime, mTvIdentity;
    private LinearLayout mSkill, mRequest, mCollection, mOrder, mModify;
    private RelativeLayout mWallet, mVip, mAuthentication, mInvitation, mSetting;
    private ImageView mIvHead, mIvGender;
//    private SelectCountModel models;
    private ArrayList<String> mPhotoList;
    private UserModel model;
    private Bundle bundle;

    @Override
    protected void instanceRootView(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.fragment_personal, null);
    }

    @Override
    protected void findViews() {
          mTvTitle = findView(R.id.title_layout_tv_title);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTvTitle.setText("看点");
        model = SharedPreferencesManager.getInstance().getUserData(getActivity());
        bundle = new Bundle();
        bundle.putString("userId", model.getUserId());
        bundle.putString("type", "0");
        initData();
    }

    private void initData() {
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
//            case R.id.fragment_personal_iv_head://头像放大
//                mPhotoList = new ArrayList<>();
//                mPhotoList.add(models.getDefaultAvatar().get(0).getUrl());
//                bundle.putStringArrayList("photolist", mPhotoList);
//                openActivity(PhotoPreActivity.class, bundle);
//                break;
//            case R.id.fragment_personal_ll_modify://修改个人信息
//                bundle.putString("head", models.getDefaultAvatar().get(0).getUrl());
//                bundle.putString("sex", models.getUserGender() + "");
//                bundle.putString("name", models.getUserNick());
//                openActivity(EditingInfoActivity.class, bundle);
//                break;
//            case R.id.fragment_personal_ll_skill: //我的技能
//                openActivity(MySkillsActivity.class, bundle);
//                break;
//            case R.id.fragment_personal_ll_request: //我的需求
//                openActivity(MyRequestActivity.class, bundle);
//                break;
//            case R.id.fragment_personal_ll_collection: //我的收藏
//                openActivity(MyCollectionActivity.class);
//                break;
//            case R.id.fragment_personal_ll_order_quantity: //已接单数
//                openActivity(OrderNumListActivity.class);
//                break;
//            case R.id.fragment_personal_rl_wallet: //我的钱包
//                openActivity(WalletActivity.class);
//                break;
//            case R.id.fragment_personal_rl_vip: //VIP申请
//                openActivity(VipApplyActivity.class);
//                break;
//            case R.id.fragment_personal_rl_authentication: //认证中心
//                openActivity(AuthenticationActivity.class);
//                break;
//            case R.id.fragment_personal_rl_invitation: //邀请好友
//                openActivity(InvitationActivity.class);
//                break;
//            case R.id.fragment_personal_rl_setting: //设置
//                openActivity(SeetingActivity.class);
//                break;

        }
    }
}
