package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wowo.wowo.Model.UserModel;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.SharedPreferencesManager;
import com.wowo.wowo.Views.CircleImageView.CircleImageView;


/**
 * desc  : 底部分享弹出框
 */

public class GroupCodeDialog extends Dialog {
    public GroupCodeDialog(@NonNull Context context) {
        super(context);
    }

    public GroupCodeDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context mContext;
        private OnSureClickListener mListener;
        private UserModel userModel;

        public Builder(Context context) {
            this.mContext = context;
            userModel = SharedPreferencesManager.getInstance().getUserData(mContext);
        }

        //这里，我们定义一个接口
        public interface OnSureClickListener {
            public void onClick();
        }


        //写一个设置接口监听的方法
        public void setOnSureClickListener(OnSureClickListener listener) {
            mListener = listener;
        }

        public GroupCodeDialog create(String type, String id) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final GroupCodeDialog dialog = new GroupCodeDialog(mContext, R.style.BottomDialog);
            View layout = inflater.inflate(R.layout.dialog_groupcode, null);
            CircleImageView mCi = (CircleImageView) layout.findViewById(R.id.dialog_group_code_ci_head);
            ImageView mIvCode = (ImageView) layout.findViewById(R.id.dialog_group_code_iv_code);
            TextView mTvname = (TextView) layout.findViewById(R.id.dialog_group_code_tv_name);
            TextView mCancel = (TextView)layout.findViewById(R.id.dialog_tv_cancel);
            mTvname.setText(type);
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

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
