package com.wowo.wowo.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wowo.wowo.Base.BaseActivity;
//import com.wowo.wowo.Personal.UAgreementActivity;
import com.wowo.wowo.Model.UserModel;
import com.wowo.wowo.Model.isRegisterModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.CheckUtils;
import com.wowo.wowo.Utils.CountDownTimerUtils;
import com.wowo.wowo.Utils.Encrypt;
import com.wowo.wowo.Utils.SharedPreferencesManager;
//import com.wowo.wowo.rxjava.BaseResponse;
//import com.wowo.wowo.rxjava.ProgressSubscriber;
//import com.wowo.wowo.rxjava.RetrofitAPIManager;
//import com.wowo.wowo.rxjava.SubscriberOnNextListener;

//import cn.jpush.sms.SMSSDK;
//import cn.jpush.sms.listener.SmscheckListener;
//import cn.jpush.sms.listener.SmscodeListener;

/**
 * desc  : 登录页面
 */

public class LoginActivity extends BaseActivity {
    private Button mBtnLogin, mBtnGetCode;
    private EditText mEtPhone, mEtCode, mEtACode;
    private CountDownTimerUtils mCoutDown;
    private String mPhoneNum, mCode;
    private Bundle bundle;
    private isRegisterModel model;
    private UserModel userModel;
    private String mACode;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String content = msg.obj.toString();
            Toast.makeText(LoginActivity.this, content, Toast.LENGTH_SHORT).show();
            mCoutDown.onFinish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        init();
        initListener();
    }

    private void initListener() {
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {
                    mBtnLogin.setEnabled(true);
                    mBtnLogin.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtnLogin.setEnabled(false);
                    mBtnLogin.setTextColor(getResources().getColor(R.color.login_btn_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        mCoutDown = new CountDownTimerUtils(mBtnGetCode, 60000, 1000, this);
        userModel = SharedPreferencesManager.getInstance().getUserData(this);
    }

    public void findViews() {
        mBtnLogin = findView(R.id.login_btn_login);
        mBtnGetCode = findView(R.id.login_tv_getcode);
        mEtPhone = findView(R.id.login_et_phone_num);
        mEtCode = findView(R.id.login_et_code);
        mEtACode = findView(R.id.login_et_request_code);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login://登录
                bundle = new Bundle();
                mPhoneNum = mEtPhone.getText().toString();
                mCode = mEtCode.getText().toString();
                mACode = mEtACode.getText().toString();
                if (mPhoneNum == null || mPhoneNum.isEmpty()) {
                    showToast("手机号码不能为空");
                    return;
                } else if (!CheckUtils.isMobile(mPhoneNum)) {
                    showToast("手机号码格式不正确");
                    return;
                }
                if (mCode == null || mCode.isEmpty()) {
                    showToast("验证码不能为空");
                    return;
                }
                openActivity(HomePageActivity.class);
//                SMSSDK.getInstance().checkSmsCode(mPhoneNum, mCode, new SmscheckListener() {
//                    @Override
//                    public void checkCodeSuccess(String s) {
//                        bundle.putString("phone", mPhoneNum);
//                        bundle.putString("code", mACode);
//                        if (!mACode.isEmpty()) {
//                            SubscriberOnNextListener mListener = new SubscriberOnNextListener<BaseResponse<String>>() {
//                                @Override
//                                public void onNext(BaseResponse<String> baseResponse) {
//                                    if (baseResponse.data.equals("0")) {
//                                        showToast("邀请码错误或不存在(区分大小写)");
//                                    } else {
//                                        isRegister();
//                                    }
//                                }
//                            };
//                            RetrofitAPIManager.getInstance()
//                                    .queryInviteCode(new ProgressSubscriber<BaseResponse<String>>(mListener, LoginActivity.this, 0), mACode, Encrypt.base64(Encrypt.base64(userModel.getUserId())));
//                        } else {
//                            isRegister();
//                        }
//                    }
//
//                    @Override
//                    public void checkCodeFail(int i, String s) {
////                        showToast("验证码错误");
//
//                        mHandler.obtainMessage(0, "验证码错误").sendToTarget();
////                        mCoutDown.onFinish();
//                    }
//                });
                break;
            case R.id.login_tv_getcode://获取验证码
                mPhoneNum = mEtPhone.getText().toString();
                if (mPhoneNum == null || mPhoneNum.isEmpty()) {
                    showToast("手机号码不能为空");
                    return;
                } else if (!CheckUtils.isMobile(mPhoneNum)) {
                    showToast("手机号码格式不正确");
                    return;
                }
                mCoutDown.start();
//                SMSSDK.getInstance().getSmsCode(mPhoneNum, "1", new SmscodeListener() {
//                    @Override
//                    public void getCodeSuccess(String s) {
//
//                    }
//
//                    @Override
//                    public void getCodeFail(int i, String s) {
////                        showToast("获取验证码失败");
//                        mHandler.obtainMessage(0, "获取验证码失败").sendToTarget();
//                    }
//                });

                break;
            case R.id.login_tv_provision:
                openActivity(UAgreementActivity.class);
                break;
        }
    }

    /**
     * 判断用户是否注册过 (因未设计账户体系)..
     */
//    private void isRegister() {
//        SubscriberOnNextListener listener = new SubscriberOnNextListener<BaseResponse<isRegisterModel>>() {
//            @Override
//            public void onNext(BaseResponse<isRegisterModel> baseResponse) {
//                model = baseResponse.data;
//                switch (model.getStatus()) {
//                    case "0"://未注册
//                        openActivity(DataFillingActivity.class, bundle);
//                        break;
//                    case "1"://已注册
//                        openActivity(HomePageActivity.class);
//                        userModel.setUserPhone(model.getPhone());
//                        userModel.setUserHead(model.getDefaultAvatar().get(0).getUrl());
//                        userModel.setUserId(model.getUserId());
//                        userModel.setUserName(model.getUserNick());
//                        userModel.setUserSex(model.getUserGender());
//                        SharedPreferencesManager.getInstance().putUserData(LoginActivity.this, userModel);
//                        break;
//                }
//            }
//        };
//        RetrofitAPIManager.getInstance().isRegister(new ProgressSubscriber<BaseResponse<isRegisterModel>>(listener, LoginActivity.this, 0), mPhoneNum);
//    }

}
