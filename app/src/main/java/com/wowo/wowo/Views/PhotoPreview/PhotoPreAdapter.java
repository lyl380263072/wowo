package com.wowo.wowo.Views.PhotoPreview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wowo.wowo.Utils.GlideUtils;

import java.util.ArrayList;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2018/1/19 0019
 * desc  :
 */
public class PhotoPreAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> mPhotoList;

    public PhotoPreAdapter(Context context, ArrayList<String> mPhotoList) {
        this.context = context;
        this.mPhotoList = mPhotoList;
    }
    @Override
    public int getCount() {
        return mPhotoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        GlideUtils.loadImageView(context,mPhotoList.get(position),imageView);
        container.addView(imageView);
        return imageView;
    }

}
