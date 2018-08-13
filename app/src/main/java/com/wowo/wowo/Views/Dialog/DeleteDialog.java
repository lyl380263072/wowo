package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.R;


public class DeleteDialog extends Dialog {
    public DeleteDialog(@NonNull Context context) {
        super(context);
    }

    public DeleteDialog(@NonNull Context context, @StyleRes int themeResId) {
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
        public DeleteDialog create(String title,String value) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final DeleteDialog dialog = new DeleteDialog(mContext, R.style.ReportDialogStyle);
            View layout = inflater.inflate(R.layout.dialog_delete, null);
            TextView mTvTitle = (TextView) layout.findViewById(R.id.dialog_delete_tv_title);
            TextView mTvValue = (TextView) layout.findViewById(R.id.dialog_delete_tv_value);
            TextView mTvCancel = (TextView) layout.findViewById(R.id.dialog_delete_tv_cancel);
            TextView mTvSure = (TextView) layout.findViewById(R.id.dialog_delete_tv_sure);
            mTvTitle.setText(title);
            mTvValue.setText(value);
            mTvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            mTvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    mListener.onClick();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setWindowAnimations(R.style.AnimBottom);
            dialog.setContentView(layout);
            return dialog;

        }
    }

}
