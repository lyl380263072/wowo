package com.wowo.wowo.Utils;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;


/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/8/3
 * desc  : 验证码倒计时
 */

public class CountDownTimerUtils extends CountDownTimer {

    private Button mBtn;
    private Context context;
    public CountDownTimerUtils(Button mBtn, long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
        this.mBtn = mBtn;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mBtn.setEnabled(false);//设置不能操作
        mBtn.setText(millisUntilFinished / 1000 + "s");  //设置倒计时时间
//        mBtn.setBackgroundResource(R.drawable.code_backround);//设置按钮背景色

        SpannableString spannableString = new SpannableString(mBtn.getText().toString());
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);//将倒计时的时间设置为红色
        mBtn.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mBtn.setText("获取验证码");
//        mBtn.setTextColorrcesUtil.getColor(context,R.color.login_backround));
        mBtn.setEnabled(true);//重新获得点击
//        mBtn.setBackgroundResource(R.drawable.login_edittext);  //还原背景色
    }
}
