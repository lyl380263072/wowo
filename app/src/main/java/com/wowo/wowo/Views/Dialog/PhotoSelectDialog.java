package com.wowo.wowo.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wowo.wowo.R;

/**
 * desc  : 添加图片方式弹出框
 */

public class PhotoSelectDialog extends Dialog {

    public PhotoSelectDialog(@NonNull Context context) {
        super(context);
    }

    public PhotoSelectDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    public static class Builder {
        private Context context;
        private OnItemClickListener mListener;
        private static final int PHOTO = 0; //拍照
        private static final int ALBUM = 1; //相册
        public Builder(Context context) {
            this.context = context;
        }
        //这里，我们定义一个接口
        public interface OnItemClickListener  {
            public void onItemClick(int tag);
        }
        //写一个设置接口监听的方法
        public void setOnItemClickListener(OnItemClickListener listener) {
            mListener = listener;
        }
        public PhotoSelectDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final PhotoSelectDialog dialog = new PhotoSelectDialog(context, R.style.ReportDialogStyle);
            View layout = inflater.inflate(R.layout.photo_select_dialog, null);
            TextView mPhoto = (TextView) layout.findViewById(R.id.dialog_tv_photo);
            TextView mAlbum = (TextView) layout.findViewById(R.id.dialog_tv_album);
            mPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(PHOTO);
                    dialog.dismiss();
                }
            });
            mAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(ALBUM);
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setWindowAnimations(R.style.AnimBottom);
            dialog.setContentView(layout);
            return dialog;
        }



    }
}
