package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.Model.UserModel;
//import com.wowo.wowo.Personal.RequestDetailShareModel;
//import com.wowo.wowo.Personal.ServiceDetailShareModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.CheckAPPisHave;
import com.wowo.wowo.Utils.Encrypt;
import com.wowo.wowo.Utils.SharedPreferencesManager;
//import com.wowo.wowo.rxjava.BaseResponse;
//import com.wowo.wowo.rxjava.ProgressSubscriber;
//import com.wowo.wowo.rxjava.RetrofitAPIManager;
//import com.wowo.wowo.rxjava.SubscriberOnNextListener;

import java.util.HashMap;
//
//import cn.jiguang.share.android.api.JShareInterface;
//import cn.jiguang.share.android.api.PlatActionListener;
//import cn.jiguang.share.android.api.Platform;
//import cn.jiguang.share.android.api.ShareParams;
//import cn.jiguang.share.qqmodel.QQ;
//import cn.jiguang.share.wechat.Wechat;
//import cn.jiguang.share.weibo.SinaWeibo;

/**
 * desc  : 底部分享弹出框
 */

public class BottomDialog extends Dialog {
    public BottomDialog(@NonNull Context context) {
        super(context);
    }

    public BottomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context mContext;
        private OnSureClickListener mListener;
        String title = "";
        String text = "";
        String pic = "";
        String url = "";
        private UserModel userModel;

        public Builder(Context context) {
            this.mContext = context;
            userModel = SharedPreferencesManager.getInstance().getUserData(mContext);
        }

        //这里，我们定义一个接口
        public interface OnSureClickListener {
            public void onClick();
        }

//        //根据不同平台分享
//        private void Share(final String type, final String title, final String text, String pic, final String url) {
//                    ShareParams shareParams = new ShareParams();
//                    //设置分享的数据类型
//                    shareParams.setShareType(Platform.SHARE_WEBPAGE);
//                    shareParams.setText(text);
//                    shareParams.setTitle(title);
//                    shareParams.setUrl(url);
//                    shareParams.setImageData(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.app_icon));
//                    //调用分享接口share ，分享到QQ平台。
//                    JShareInterface.share(type, shareParams, new PlatActionListener() {
//                        @Override
//                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                            Log.e("share", "onComplete");
//                        }
//
//                        @Override
//                        public void onError(Platform platform, int i, int i1, Throwable throwable) {
//                            Log.e("share", "onError");
//
//                        }
//
//                        @Override
//                        public void onCancel(Platform platform, int i) {
//                            Log.e("share", "onCancel");
//
//                        }
//                    });
//                }

        //创建分享参数


        //写一个设置接口监听的方法
        public void setOnSureClickListener(OnSureClickListener listener) {
            mListener = listener;
        }

        public BottomDialog create(String type, String id) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final BottomDialog dialog = new BottomDialog(mContext, R.style.BottomDialog);
            View layout = inflater.inflate(R.layout.dialog_bottomshare, null);
            LinearLayout mQQ = (LinearLayout) layout.findViewById(R.id.dialog_bottom_qq);
            LinearLayout mWX = (LinearLayout) layout.findViewById(R.id.dialog_bottom_wx);
            TextView mCancel = (TextView)layout.findViewById(R.id.dialog_tv_cancel);
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

//            switch (type) {
//                case "0":
//                    SubscriberOnNextListener listener = new SubscriberOnNextListener<BaseResponse<ServiceDetailShareModel>>() {
//                        @Override
//                        public void onNext(BaseResponse<ServiceDetailShareModel> baseResponse) {
//                            title = baseResponse.data.getTitle();
//                            text = baseResponse.data.getServiceContent();
//                            pic = baseResponse.data.getPicture();
//                            url = baseResponse.data.getServiceUrl();
//                        }
//                    };
//                    RetrofitAPIManager.getInstance().getService(new ProgressSubscriber
//                            <BaseResponse<ServiceDetailShareModel>>(listener, (BaseActivity) mContext, 0), id, Encrypt.base64(userModel.getUserId()));
//                    break;
//                case "1":
//                    SubscriberOnNextListener mListenerDemand = new SubscriberOnNextListener<BaseResponse<RequestDetailShareModel>>() {
//                        @Override
//                        public void onNext(BaseResponse<RequestDetailShareModel> baseResponse) {
//                            title = baseResponse.data.getTitle();
//                            text = baseResponse.data.getDemandContent();
//                            pic = baseResponse.data.getPicture();
//                            url = baseResponse.data.getDemandUrl();
//                        }
//                    };
//                    RetrofitAPIManager.getInstance().getDemand(new ProgressSubscriber
//                            <BaseResponse<RequestDetailShareModel>>(mListenerDemand, (BaseActivity) mContext, 0), id, Encrypt.base64(userModel.getUserId()));
//                    break;
//            }
//            mQQ.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (CheckAPPisHave.isQQClientAvailable(mContext)) {
//                        Share(QQ.Name, title, text, pic, url);
//                        dialog.dismiss();
//
//
//                    } else {
//                        ((BaseActivity) mContext).showToast("您还没有安装QQ,请先安装QQ客户端");
//                    }
//                }
//            });
//            mWX.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (CheckAPPisHave.isWeixinAvilible(mContext)) {
//                        Share(Wechat.Name, title, text, pic, url);
//                        dialog.dismiss();
//                    } else {
//                        ((BaseActivity) mContext).showToast("您还没有安装微信,请先安装微信客户端");
//                    }
//                }
//            });
//            mSina.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Share(SinaWeibo.Name, title, text, pic, url);
//                    dialog.dismiss();
//                }
//            });
            dialog.setCanceledOnTouchOutside(true);
            dialog.setContentView(layout);
            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
            layout.setLayoutParams(layoutParams);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
            return dialog;
        }

    }

}
