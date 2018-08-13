package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.R;
import com.wowo.wowo.Views.TextView.JustifyTextView;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/12/6
 * desc  : 警告弹出框 可自定义内容
 */

public class WarningDialog extends Dialog {
    public WarningDialog(@NonNull Context context) {
        super(context);
    }

    public WarningDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    public static class Builder {
        private Context mContext;
        private OnSureClickListener mListener;
        public Builder(Context context) {
            this.mContext = context;
        }
        //这里，我们定义一个接口
        public interface OnSureClickListener  {
            public void onClick();
        }
        //写一个设置接口监听的方法
        public void setOnSureClickListener(OnSureClickListener listener) {
            mListener = listener;
        }
        public WarningDialog create(String value) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final WarningDialog dialog = new WarningDialog(mContext, R.style.ReportDialogStyle);
            View layout = inflater.inflate(R.layout.dialog_warning, null);
            TextView mTvSure = (TextView) layout.findViewById(R.id.dialog_warning_tv_sure);
            JustifyTextView mTvValue = (JustifyTextView) layout.findViewById(R.id.dialog_tv_value);
            mTvValue.setText(value);
            mTvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setWindowAnimations(R.style.AnimBottom);
            dialog.setContentView(layout);
            return dialog;
        }
    }

}
