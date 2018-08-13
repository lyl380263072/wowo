package com.wowo.wowo.Views.SweetAlert;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * 弹出框
 * Created by andy on 2016/6/13.
 */

public class AlertHelper {

    private Context context;

    private SweetAlertDialog dialog;

    public AlertHelper(Context context){
        this.context = context;
    }

    /**
     * 显示进度框
     * @param title
     * @param color -1 默认颜色
     */
    public void showProgresstDialog(String title,int color){
        if(dialog == null){
            dialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        }
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.showContentText(false);
        dialog.showConfirmButton(false);
        dialog.showCancelButton(false);
        dialog.setTitleText(title);
        if(color != -1) {
            dialog.getProgressHelper().setBarColor(color);
        }

        dialog.show();
    }

    /**
     * 普通文字提示框
     * @param title
     * @param confirmText 按钮文本1
     * @param cancelText 按钮文本2
     * @param confirmClickListener 按钮1事件
     * @param cancelClickListener 按钮2事件
     */
    public void showNormalDialog(String title,String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmClickListener,SweetAlertDialog.OnSweetClickListener cancelClickListener){
        if(dialog == null){
            dialog = new SweetAlertDialog(context,SweetAlertDialog.NORMAL_TYPE);
        }
        dialog.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
        dialog.setTitleText(title);

        dialog.showConfirmButton(true);
        if(confirmText==null){
            dialog.setConfirmText("是");
        }else{
            dialog.setConfirmText(confirmText);
        }

        if(cancelText == null){
            dialog.showCancelButton(false);
        }else{
            dialog.setConfirmText(confirmText);
            dialog.showCancelButton(true);
        }

        dialog.setConfirmClickListener(confirmClickListener);
        dialog.setCancelClickListener(cancelClickListener);

        dialog.show();
    }

    /**
     * 带图片提示框
     * @param title
     * @param drawable
     */
    public void showImageDialog(String title, Drawable drawable){
        if(dialog == null){
            dialog = new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        }
        dialog.changeAlertType(SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        dialog.setTitleText(title);
        dialog.setCustomImage(drawable);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        dialog.setConfirmText("是");
        dialog.show();
    }

    /**
     * 连接成功提示框
     * @param title
     */
    public void showSuccessDialog(String title){
        if(dialog == null){
            dialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
        }
        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        dialog.setConfirmText("是");
        dialog.show();
    }

    /**
     * 提示框带标题
     * @param title
     * @param confirmText 按钮文本1
     * @param cancelText 按钮文本2
     * @param confirmClickListener 按钮1事件
     * @param cancelClickListener 按钮2事件
     */
    public void showPromptDialog(String title, String confirmText, String cancelText, SweetAlertDialog.OnSweetClickListener confirmClickListener,SweetAlertDialog.OnSweetClickListener cancelClickListener){
        if(dialog == null){
            dialog = new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE);
        }
        dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
        dialog.showContentText(false);
        dialog.showConfirmButton(false);
        dialog.showCancelButton(false);
        dialog.setTitleText(title);

        dialog.showConfirmButton(true);
        if(confirmText==null){
            dialog.setConfirmText("是");
        }else{
            dialog.setConfirmText(confirmText);
        }

        if(cancelText == null){
            dialog.showCancelButton(false);
        }else{
            dialog.setConfirmText(confirmText);
            dialog.showCancelButton(true);
        }

        dialog.setConfirmClickListener(confirmClickListener);
        dialog.setCancelClickListener(cancelClickListener);

        dialog.show();
    }

    /**
     * 获取弹出框实例
     * @return
     */
    public SweetAlertDialog getDialog(){
        if(dialog == null){
            dialog = new SweetAlertDialog(context);
        }
        return dialog;
    }

    /**
     * 关闭
     */
    public void close(){
        if(dialog != null){
            dialog.dismiss();
        }
    }
}
