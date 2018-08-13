package com.wowo.wowo.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/7/26
 * desc  :
 */

/**
 * ScrollView并没有实现滚动监听，所以我们必须自行实现对ScrollView的监听，
 * 我们很自然的想到在onTouchEvent()方法中实现对滚动Y轴进行监听
 * ScrollView的滚动Y值进行监听
 */
public class HoverScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    //回调接口的对象
    private OnScrollListener onScrollListener;
    private int downX;

    private int downY;

    private int mTouchSlop;


    public HoverScrollView(Context context) {
        super(context);
    }

    public HoverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);
    }

    public HoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);

    }

    /**
     * 为了防止SrcollView嵌套横向listview滑动时出现不流畅的情况
     */
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if(Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:

                downX = (int) ev.getRawX();

                downY = (int) ev.getRawY();

                break;

            case MotionEvent.ACTION_MOVE:

                int moveY = (int) ev.getRawY();

                if (Math.abs(moveY - downY) > mTouchSlop) {

                    return true;

                }

        }
        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }

    /**在滑动的时候调用我们自己写的回调方法，来获取滑动距离*/
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(onScrollListener!=null){
            onScrollListener.onScroll(t);
        }
    }
    /**滑动回调监听的接口*/
    public interface OnScrollListener{
        /**回调方法，返回MyScrollView在Y轴方向的滑动距离*/
        public void onScroll(int scrollY);
    }

    public OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

}
