//package com.wowo.wowo.Views.PopupWindow;
//
//import android.animation.Animator;
//import android.animation.ObjectAnimator;
//import android.animation.ValueAnimator;
//import android.app.Activity;
//import android.graphics.Rect;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//
//import com.infinite_yx.www.infinite.Base.BaseActivity;
//import com.infinite_yx.www.infinite.HomePage.ReleaseRequestActivity;
//import com.infinite_yx.www.infinite.HomePage.ReleaseServiceActivity;
//import com.infinite_yx.www.infinite.R;
//
///**
// * 发布弹出窗口
// */
//public class FloatWindow extends PopupWindow {
//
//    private Activity mContext;
//    private int statusBarHeight;
//    private Handler mHandler = new Handler();
//    private Bundle bundle;
//    private RelativeLayout layout;
//
//    public FloatWindow(Activity context) {
//        mContext = context;
//    }
//
//    public void init() {
//        Rect frame = new Rect();
//        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        statusBarHeight = frame.top;
//        DisplayMetrics metrics = new DisplayMetrics();
//        mContext.getWindowManager().getDefaultDisplay()
//                .getMetrics(metrics);
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//
//    }
//
//
//    public void showReleaseWindow(View anchor,double lat,double lon) {
//        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.popup_window_release, null);
//        setContentView(layout);
//        bundle = new Bundle();
//        bundle.putDouble("lat",lat);
//        bundle.putDouble("lon",lon);
//        Button mBtnRelease = (Button) layout.findViewById(R.id.popup_window_btn_release);
//        LinearLayout mBtnReleaseService = (LinearLayout) layout.findViewById(R.id.popup_window_ll_service);
//        LinearLayout mBtnReleaseRequest = (LinearLayout) layout.findViewById(R.id.popup_window_ll_request);
//        mBtnReleaseService.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((BaseActivity) mContext).openActivity(ReleaseServiceActivity.class,bundle);
//
//            }
//        });
//        mBtnReleaseRequest.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((BaseActivity) mContext).openActivity(ReleaseRequestActivity.class,bundle);
//            }
//        });
////        mBtnRelease.setOnClickListener(new OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////                if (isShowing()) {
////                    closeAnimation(layout_wx_pay_entry);
////                }
////            }
////
////        });
//        setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    closeAnimation(layout);
//                }
//                return false;
//            }
//        });
//        setBackgroundDrawable(new BitmapDrawable());
//        setOutsideTouchable(true);
//        setFocusable(true);
//        showAnimation(layout);
//        showAtLocation(anchor, Gravity.CENTER, 0, statusBarHeight);
//
//    }
//
//    private void showAnimation(ViewGroup layout) {
//        //遍历根视图下的子视图
//        for (int i = 0; i < layout.getChildCount(); i++) {
//            final View child = layout.getChildAt(i);
//            //忽略关闭组件
//            if (child.getId() == R.id.popup_window_btn_release) {
//                continue;
//            }
////            child.setOnClickListener(this);
//            child.setVisibility(View.INVISIBLE);
//
//            //延迟显示每个子视图
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    child.setVisibility(View.VISIBLE);
//                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
//                    fadeAnim.setDuration(300);
//                    KickBackAnimator kickAnimator = new KickBackAnimator();
//                    kickAnimator.setDuration(150);
//                    fadeAnim.setEvaluator(kickAnimator);
//                    fadeAnim.start();
//                }
//            }, i * 70);
//        }
//    }
//
//    private void closeAnimation(ViewGroup layout) {
//        //遍历根视图下的子视图
//        for (int i = 0; i < layout.getChildCount(); i++) {
//            final View child = layout.getChildAt(i);
//            //忽略关闭组件
//            if (child.getId() == R.id.popup_window_btn_release) {
//                continue;
//            }
////            child.setOnClickListener(this);
//            //延迟隐藏每个子视图
//            mHandler.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    child.setVisibility(View.VISIBLE);
//                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
//                    fadeAnim.setDuration(300);
//                    KickBackAnimator kickAnimator = new KickBackAnimator();
//                    kickAnimator.setDuration(150);
//                    fadeAnim.setEvaluator(kickAnimator);
//                    fadeAnim.start();
//                    fadeAnim.addListener(new Animator.AnimatorListener() {
//
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animator animation) {
//                            // TODO Auto-generated method stub
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            child.setVisibility(View.INVISIBLE);
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animation) {
//                            // TODO Auto-generated method stub
//
//                        }
//                    });
//                }
//            }, (layout.getChildCount() - i - 1) * 60);
//
//            if (child.getId() == R.id.popup_window_ll_request) {
//                mHandler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        dismiss();
//                    }
//                }, (layout.getChildCount() - i) * 30 + 260);
//            }
//        }
//
//    }
//
////    @Override
////    public void onClick(View v) {
////        switch (v.getId()) {
////            case R.id.popup_window_ll_service:
////                break;
////            case R.id.popup_window_ll_request:
////                break;
////            default:
////                break;
////        }
////    }
//
//
//}
