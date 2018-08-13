package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Views.PwdInPutView.PwdInputView;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/11/27
 * desc  : 验证码弹出框
 */

public class CodeInputDialog extends Dialog {


    public CodeInputDialog(Context context) {
        super(context);
    }

    public CodeInputDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private OnSureClickListener mListener;

        public Builder(Context context) {
            this.context = context;
        }

        //这里，我们定义一个接口
        public interface OnSureClickListener {
            public void onClick(String code, Dialog dialog);
        }

        //写一个设置接口监听的方法
        public void setOnSureClickListener(OnSureClickListener listener) {
            mListener = listener;
        }

        public CodeInputDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CodeInputDialog dialog = new CodeInputDialog(context, R.style.ReceiveOrderDialogStyle);
            View layout = inflater.inflate(R.layout.dialog_codeinput, null);
            TextView mTvComplete = (TextView) layout.findViewById(R.id.dialog_codeinput_tv_complete);
            final PwdInputView mInput = (PwdInputView) layout.findViewById(R.id.dialog_codeinput_piv);
            mInput.setPwdInputViewType(PwdInputView.ViewType.DEFAULT);
            mInput.setShadowPasswords(true);//设置验证码可见
            mTvComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = mInput.getText().toString();
                    if (code.length() < 6) {
                        ((BaseActivity) context).showToast("请输入完整的验证码");
                        return;
                    }
                    mListener.onClick(code, dialog);
                }
            });
            dialog.setContentView(layout);
            //屏蔽后退键
            dialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            return dialog;
        }

    }
}
