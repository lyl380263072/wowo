package com.wowo.wowo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import com.wowo.wowo.Utils.ScreenUtils;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/12/19
 * desc  : 解决listview滑动与item点击的冲突
 */

public class SolveClickTouchConflictLayout extends RelativeLayout {
    public SolveClickTouchConflictLayout(Context context) {
        this(context, null);
    }

    public SolveClickTouchConflictLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public SolveClickTouchConflictLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initView() {

    }

    private boolean mScrolling;
    private float touchDownX;
    private float touchDownY;

    //拦截触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                touchDownY = event.getY();
                mScrolling = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(touchDownX - event.getX()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()||Math.abs(touchDownY - event.getY()) >= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    mScrolling = true;
                } else {
                    mScrolling = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mScrolling = false;
                break;
        }
        return mScrolling;
    }

    float x2 = 0;
    float y2 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;//消费触摸事件
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //左滑
                if (touchDownX - x2 > ScreenUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onRightToLeftSlide();
                    }
                }
                //右滑
                if (touchDownX - x2 < -ScreenUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onLeftToRightSlide();
                    }
                }
                //上滑
                if (touchDownY - y2 > ScreenUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onDownToUpSlide();
                    }
                }
                //下滑
                if (touchDownY - y2 < -ScreenUtils.dip2px(getContext(), 40)) {
                    if(mOnSlideListener!=null){
                        mOnSlideListener.onUpToDownSlide();
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private OnSlideListener mOnSlideListener;

    public OnSlideListener getOnSlideListener() {
        return mOnSlideListener;
    }

    public void setmSetOnSlideListener(OnSlideListener mOnSlideListener) {
        this.mOnSlideListener = mOnSlideListener;
    }

    public interface OnSlideListener{
        void onRightToLeftSlide();
        void onLeftToRightSlide();
        void onUpToDownSlide();
        void onDownToUpSlide();
    }

}
