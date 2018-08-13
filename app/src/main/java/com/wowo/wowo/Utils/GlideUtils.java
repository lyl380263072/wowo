package com.wowo.wowo.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
 import com.wowo.wowo.R;


/** Glide图片缓存
 */

public class GlideUtils {
    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }
    //加载圆角图片
    public static void loadImageViewForRoundImage(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).dontAnimate().transform(new GlideRoundTransform(mContext)).centerCrop().into(mImageView);
    }
    //图片请求带cookie
    public static void loadImageViewForCookie(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(new GlideUrl(path,new LazyHeaders.Builder().addHeader("Cookie",SharedPreferencesManager.getInstance()
                .getData(mContext,"Cookie","cookie")).build())).error(R.mipmap.work_pic_loading2x).placeholder(R.mipmap.work_pic_loading2x).into(mImageView);
    }
    //头像
    public static void loadImageViewForHead(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).override(100,100).dontAnimate()
                .transform(new GlideCircleTransform(mContext)).centerCrop()
                .placeholder(R.mipmap.deg_image_sel).error(R.mipmap.deg_image_sel).into(mImageView);
    }
    //头像 关闭缓存
    public static void loadImageViewNoCache(Context mContext, String path,ImageView imageView) {
        Glide.with(mContext).load(path).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).transform(new GlideCircleTransform(mContext)).centerCrop().into(imageView);
    }
    //加载指定大小
    public static void loadImageViewSize(Context mContext, String path, int width, int height, ImageView mImageView) {
        Glide.with(mContext).load(path).override(width, height).into(mImageView);
    }
    //设置加载中以及加载失败图片
    public static void loadImageViewLoding(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public static void loadImageViewLodingSize(Context mContext, String path, int width, int height, ImageView mImageView, int lodingImage, int errorImageView) {
        Glide.with(mContext).load(path).override(width, height).placeholder(lodingImage).error(errorImageView).into(mImageView);
    }
    //设置跳过内存缓存
    public static void loadImageViewCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }

    //设置下载优先级
    public static void loadImageViewPriority(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */

    //设置缓存策略
    public static void loadImageViewDiskCache(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).dontAnimate().transform(new GlideCircleTransform(mContext)).centerCrop().placeholder(R.mipmap.deg_image_sel).error(R.mipmap.deg_image_sel).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */

    //设置加载动画
    public static void loadImageViewAnim(Context mContext, String path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).animate(anim).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */

    //设置缩略图支持
    public static void loadImageViewThumbnail(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */

    //设置动态转换
    public static void loadImageViewCrop(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).centerCrop().into(mImageView);
    }

    //设置动态GIF加载方式
    public static void loadImageViewDynamicGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asGif().into(mImageView);
    }

    //设置静态GIF加载方式
    public static void loadImageViewStaticGif(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).asBitmap().into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<String, GlideDrawable> requstlistener) {
        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public static void loadImageViewContent(Context mContext, String path, SimpleTarget<GlideDrawable> simpleTarget) {
        Glide.with(mContext).load(path).centerCrop().dontAnimate().transform(new GlideCircleTransform(mContext)).centerCrop().placeholder(R.mipmap.deg_image_sel).error(R.mipmap.deg_image_sel).into(simpleTarget);
    }


    //清理内存缓存
    public static void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }

}
