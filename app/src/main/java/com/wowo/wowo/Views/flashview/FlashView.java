package com.wowo.wowo.Views.flashview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wowo.wowo.R;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** 自定义轮播图
 * Created by ZhangXiaoWei on 2016/5/30.
 */
@SuppressLint("HandlerLeak")
public class FlashView extends FrameLayout {
    private ImageLoaderTools imageLoaderTools;
    private ImageHandler mhandler = new ImageHandler(new WeakReference<FlashView>(this));
    private List<String> imageUris;
    private Context context;
    private List<ImageView> imageViewsList;
    private List<ImageView> dotViewsList;
    private LinearLayout mLinearLayout;
    private ViewPager mViewPager;
    private FlashViewListener mFlashViewListener;//向外提供接口
    private boolean isTwo = false;
    private int pos;

    public FlashView(Context context) {
        this(context, null);
    }

    public FlashView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //读取该自定义控件自定义的属性
        this.context = context;
        initUI(context);
        if (!(imageUris.size() <= 0)) {
            setImageUris(imageUris);//
        }
    }

    /**
     * 设置监听
     *
     * @param mFlashViewListener
     */
    public void setOnPageClickListener(FlashViewListener mFlashViewListener) {
        this.mFlashViewListener = mFlashViewListener;
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void initUI(Context context) {
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<ImageView>();
        imageUris = new ArrayList<String>();
        imageLoaderTools = ImageLoaderTools.getInstance(context.getApplicationContext());
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_dot);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        //mFlashViewListener必须实例化
    }

    /**
     * 设置图片开始轮播
     *
     * @param imageuris 数据源
     */
    public void setImageUris(List<String> imageuris) {
        if (imageUris.size() > 0) {
            imageUris.clear();
            imageViewsList.clear();
            dotViewsList.clear();
            mLinearLayout.removeAllViews();

        }

        if (imageuris.size() <= 0)// 如果得到的图片张数为0，则增加一张默认的图片
        {

            imageUris.add("drawable://" + R.mipmap.work_pic_loading2x);
        } else {
            if (imageuris.size() == 2) {
                isTwo = true;
                imageUris.addAll(imageuris);
                imageUris.addAll(imageuris);
            } else {
                isTwo = false;
                imageUris.addAll(imageuris);
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
        lp.setMargins(5, 0, 0, 0);
        for (int i = 0; i < imageUris.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);// X和Y方向都填满
            imageLoaderTools.displayImage(imageUris.get(i), imageView);
            imageViewsList.add(imageView);
            ImageView viewDot = new ImageView(getContext());
//            if (i == 0) {
//                viewDot.setBackgroundResource(R.mipmap.dot_blue);
//            } else {
//                viewDot.setBackgroundResource(R.mipmap.dot_white);
//            }
            viewDot.setLayoutParams(lp);
//            if (isTwo)//为两张图片时加入的判断
//            {
//                if (i > 1) {
//
//                } else {
//                    dotViewsList.add(viewDot);
//                    mLinearLayout.addView(viewDot);
//                }
//            } else {
//                dotViewsList.add(viewDot);
//                mLinearLayout.addView(viewDot);
//            }

        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPageChangeListener());
        if (imageUris.size() <= 1)//图片小于等于1张时，不轮播
        {

        } else {

            // 利用反射修改自动轮播的动画持续时间
            try {

                Field field = ViewPager.class.getDeclaredField("mScroller");

                field.setAccessible(true);

                FixedSpeedScroller scroller = new FixedSpeedScroller(
                        mViewPager.getContext(), new AccelerateInterpolator());

                field.set(mViewPager, scroller);

                scroller.setmDuration(150);//动画的持续时间

                mViewPager.setCurrentItem(100 * imageViewsList.size());

                mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE,
                        ImageHandler.MSG_DELAY);

            } catch (Exception e) {

            }
        }

    }

    /**
     * 切换轮播小点的显示
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
//        for (int i = 0; i < dotViewsList.size(); i++) {
//            if (i == selectItems % dotViewsList.size()) {
//                dotViewsList.get(i).setBackgroundResource(R.mipmap.dot_blue);
//            } else {
//                dotViewsList.get(i).setBackgroundResource(R.mipmap.dot_white);
//            }
//        }
    }

    /**
     * 数据适配器
     */
    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View container, int position, Object object) {

        }

        @Override
        public Object instantiateItem(View container, int position) {

            position = position % imageViewsList.size();


            if (position < 0) {
                position = position + imageViewsList.size();

            }
            if (isTwo) {
                pos = position % 2;
            } else {
                pos = position;
            }
            final int posclick = pos;
            View view = imageViewsList.get(position);
            view.setTag(position);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mFlashViewListener != null) {
                        mFlashViewListener.onClick(posclick);
                    } else {

                    }

                }
            });
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewPager pager = (ViewPager) vp;
                pager.removeView(view);
            }
            ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (imageUris.size() <= 1) {
                return 1;
            } else {
                return Integer.MAX_VALUE;
            }

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:// 正在拖动页面时执行此处
                    mhandler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);

                    break;
                case ViewPager.SCROLL_STATE_IDLE:// 未拖动页面时执行此处
                    mhandler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                    break;
                default:
                    break;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            mhandler.sendMessage(Message.obtain(mhandler, ImageHandler.MSG_PAGE_CHANGED, pos, 0));

//            setImageBackground(pos);

        }

    }

    @SuppressWarnings("unused")
    private void destoryBitmaps() {
        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
    }


    /**
     * FixedSpeedScroller类
     * 利用反射修改自动轮播的动画持续时间
     */
    public class FixedSpeedScroller extends Scroller {
        private int mDuration = 2000;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {

            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setmDuration(int time) {
            mDuration = time;
        }

        public int getmDuration() {
            return mDuration;
        }
    }

    private static class ImageHandler extends android.os.Handler {

        protected static final int MSG_UPDATE_IMAGE = 1;

        protected static final int MSG_KEEP_SILENT = 2;

        protected static final int MSG_BREAK_SILENT = 3;

        protected static final int MSG_PAGE_CHANGED = 4;

        protected static final long MSG_DELAY = 3000;//3秒开始轮播到下一张图片

        private WeakReference<FlashView> weakReference;//弱引用，防止内存泄漏
        private int currentItem = 0;

        protected ImageHandler(WeakReference<FlashView> wk) {
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FlashView activity = weakReference.get();
            if (activity == null) {
                return;
            }
            if (activity.mhandler.hasMessages(MSG_UPDATE_IMAGE)) {
                if (currentItem > 0)// 这里必须加入currentItem>0的判断，否则不能完美的自动轮播
                {
                    activity.mhandler.removeMessages(MSG_UPDATE_IMAGE);
                }
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.mViewPager.setCurrentItem(currentItem);
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    break;
                case MSG_BREAK_SILENT:
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    currentItem = msg.arg1;
                    activity.mhandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 图片缓存设置
     */
    public static class ImageLoaderTools {

        private static ImageLoaderTools mImageLoaderWrapper;
        private static ImageLoader mImageLoader;
        private static final int DISK_CACHE_SIZE_BYTES = 50 * 1024 * 1024;//硬盘缓存的最大大小
        private static final int MEMORY_CACHE_SIZE_BYTES = 2 * 1024 * 1024;//内存缓存的最大大小

        private ImageLoaderTools(Context context){
            setImageLoader(initImageLoader(context));
        }

        public static ImageLoaderTools getInstance(Context context){
            if(mImageLoaderWrapper == null){
                mImageLoaderWrapper = new ImageLoaderTools(context);
                return mImageLoaderWrapper;
            }else{
                return mImageLoaderWrapper;
            }
        }


        private static ImageLoader initImageLoader(Context context) {
            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                    .showStubImage(R.mipmap.work_pic_loading2x)//设置图片在下载期间显示的图片
                    .showImageForEmptyUri(R.mipmap.work_pic_loading2x)// 设置图片URI为空时默认图片
                    .showImageOnFail(R.mipmap.work_pic_loading2x)//设置图片加载/解码过程中错误时候显示的图片
                    .build();

            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(defaultOptions)
                    .discCacheSize(DISK_CACHE_SIZE_BYTES)
                    .memoryCacheSize(MEMORY_CACHE_SIZE_BYTES)
                    .build();

            ImageLoader tmpIL = ImageLoader.getInstance();
            tmpIL.init(config);
            return tmpIL;

        }

        public ImageLoader getImageLoader() {
            return mImageLoader;
        }

        private static void setImageLoader(ImageLoader mImageLoader) {
            ImageLoaderTools.mImageLoader = mImageLoader;
        }


        public void displayImage(String mResName, ImageView imageView) {
            if(mResName.startsWith("http://")){
                mImageLoader.displayImage(mResName, imageView);
            }else if(mResName.startsWith("assets://"))
            {
                mImageLoader.displayImage(mResName, imageView);
            }
            else if(mResName.startsWith("file:///mnt"))
            {
                mImageLoader.displayImage(mResName, imageView);
            }
            else if(mResName.startsWith("content://"))
            {
                mImageLoader.displayImage(mResName, imageView);
            }
            else if(mResName.startsWith("drawable://"))
            {
                mImageLoader.displayImage(mResName, imageView);
            }
            else{
                Uri uri = Uri.parse(mResName);
                imageView.setImageURI(uri);
            }

        }
    }

}
