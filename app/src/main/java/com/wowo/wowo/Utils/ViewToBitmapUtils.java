package com.wowo.wowo.Utils;

import android.graphics.Bitmap;
import android.view.View;


/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2018/1/11 0011
 * desc  :
 */
public class ViewToBitmapUtils {
    public static Bitmap convertViewToBitmap(View view) {

//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//        view.layout_wx_pay_entry(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//
//        view.buildDrawingCache();
//
//        Bitmap bitmap = view.getDrawingCache();
//
//        return bitmap;


        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
