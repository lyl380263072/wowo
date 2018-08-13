package com.wowo.wowo.Activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.wowo.wowo.Base.BaseActivity;
import com.wowo.wowo.R;
import com.wowo.wowo.Utils.GlideUtils;
import com.wowo.wowo.Utils.ImageCompressionUtils;
import com.wowo.wowo.Views.CircleImageView.CircleImageView;
import com.wowo.wowo.Views.Dialog.PhotoSelectDialog;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewGroupActivity extends BaseActivity{
    private final static int REQUEST_CODE_CAMERA = 0;//相机
    private final static int REQUEST_CODE_GALLERY = 1;//相册
    private int mHeadImgName;
    private String mPicPath;

    private Bundle bundle;
    private TextView mTvTitle,mTvAction;
    private CircleImageView mCihead;
    private EditText mEtIntroduce,mEtName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgroup);
        findViews();
        init();
        initListener();
    }
    public void findViews() {
        mTvTitle = findView(R.id.title_layout_tv_title);
        mTvAction = findView(R.id.title_layout_tv_action);

        mCihead = findView(R.id.group_ic_head);
        mEtIntroduce= findView(R.id.group_et_introduce);
        mEtName= findView(R.id.group_et_name);
    }
    private void init() {
        bundle = getIntent().getExtras();
//        mEtRemark.setText(bundle.get("name").toString());
        mTvTitle.setText("信息设置");
        mTvAction.setText("完成");

    }
    private void initListener() {
        mTvAction.setOnClickListener(this);
        mCihead.setOnClickListener(this);

    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.title_layout_tv_action://完成
                final String name = mEtName.getText().toString();
                if (name == null || name.isEmpty()) {
                    showToast("请输入群名");
                    return;
                }
                if (mPicPath.isEmpty()) {
                    showToast("请选择一张照片作为头像");
                    return;
                }
                File attFile = new File("/data/data/com.wowo.wowo/head" + mHeadImgName + ".jpg");
                Log.d("attFile", "widgetClick: " + attFile);
                RequestBody Body = RequestBody.create(MediaType.parse("Multipart/form-data"), attFile);
                MultipartBody.Part part = MultipartBody.Part.createFormData("userPicture", attFile.getName(), Body);
                //
                finish();
                break;
            case R.id.group_ic_head://群组头像
                showPhotoDialog();
                break;
        }
    }

    private void setImgName(){
        mHeadImgName = (int) (Math.random() * 12345678);
    }
    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            switch (reqeustCode) {
                case REQUEST_CODE_CAMERA: //相机回调
                    setImgName();
                    ImageCompressionUtils.QualityCompressionImage(BitmapFactory.decodeFile(resultList.get(0).getPhotoPath()),
                            "/data/data/com.wowo.wowo/IMG" + mHeadImgName + ".jpg");
                    GlideUtils.loadImageView(NewGroupActivity.this,
                            "/data/data/com.wowo.wowo/IMG" + mHeadImgName + ".jpg", mCihead);
                    break;
                case REQUEST_CODE_GALLERY://相册回调
                    setImgName();
                    ImageCompressionUtils.QualityCompressionImage(BitmapFactory.decodeFile(resultList.get(0).getPhotoPath()),
                            "/data/data/com.wowo.wowo/IMG" + mHeadImgName + ".jpg");
                    GlideUtils.loadImageView(NewGroupActivity.this,
                            "/data/data/com.wowo.wowo/IMG" + mHeadImgName + ".jpg", mCihead);

                    break;
            }
            mPicPath = resultList.get(0).getPhotoPath();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };
    /**
     * 弹出添加图片方式的弹框
     */
    private void showPhotoDialog() {
        PhotoSelectDialog.Builder mDialog = new PhotoSelectDialog.Builder(this);
        mDialog.create().show();
        mDialog.setOnItemClickListener(new PhotoSelectDialog.Builder.OnItemClickListener() {
            @Override
            public void onItemClick(int tag) {
                switch (tag) {
                    case REQUEST_CODE_CAMERA: //拍照
                        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                        break;
                    case REQUEST_CODE_GALLERY: //相册
                        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, 1, mOnHanlderResultCallback);
                        break;
                }
            }
        });
    }

}
