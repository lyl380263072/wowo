package com.wowo.wowo.Appliction;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;

import com.wowo.wowo.Utils.GlideImageLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//import cn.jiguang.share.android.api.JShareInterface;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.sms.SMSSDK;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;
import io.rong.imkit.RongIM;

/**
 * Created by ZhangXiaoWei
 * 2017/11/16 0016 下午 3:06
 */

public class wowoAppliction extends MultiDexApplication {
    public static wowoAppliction mContext;
    public static List<Activity> mActivitys;
    String fontPath = "fonts/PingFang_SC_Regular.ttf";

    /**
     * 单例模式 双重加锁
     */
    public static wowoAppliction getInstance() {
        if (null == mContext) {
            synchronized(wowoAppliction.class){
                if (null == mContext){
                    mContext = new wowoAppliction();
                }
            }
        }
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        initSMS();//初始化短信服务
//        initJPush();//初始化极光推送
//        initJShare();//初始化极光分享
        initGalleryfinal();//初始化图片选择器
//        ReplaceSystemDefaultFont(this, fontPath);//修改字体
        initRongCloud();//初始化融云

    }

//    private void initJShare() {
//        JShareInterface.setDebugMode(true);
//        JShareInterface.init(this);
//    }

//    private void initJPush() {
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//    }

    private void initRongCloud() {
        RongIM.init(this);//初始化融云服务
    }

    /**
     * 修改系统自带的默认字体
     * @param context
     * @param fontPath
     */
    private void ReplaceSystemDefaultFont(Context context, String fontPath) {
        //這里我们修改的是MoNOSPACE,是因为我们在主题里给app设置的默认字体就是monospace，设置其他的也可以
        ReplaceTypefaceField("MONOSPACE", createTypeface(context, fontPath));
    }

    /**
     * 通过反射修改字体
     * @param fieldName
     * @param value
     */
    private void ReplaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }

    private void initGalleryfinal() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(1)
                .build();

        //配置imageloader
        ImageLoader imageloader = new GlideImageLoader();
        //设置核心配置信息
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageloader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    /**
     * 初始化短信服务
     */
//    private void initSMS() {
//        SMSSDK.getInstance().initSdk(mContext);
//        SMSSDK.getInstance().setIntervalTime(2*10000);
//    }

    /**
     * 添加activity到容器中
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivitys == null) {
            mActivitys = new ArrayList<Activity>();
        }
        mActivitys.add(activity);
    }

    /**
     * 退出App时调用该方法
     * 遍历所有activity并且finish。
     */
    public void destory() {
        for (Activity activity : mActivitys) {
            if (!activity.isFinishing() && activity != null) {
                activity.finish();
            }
        }
        mActivitys.clear();//清空列表取消引用
        System.exit(0);
    }
}
