package com.wowo.wowo.Utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by HelloWorld on 2018/3/26.
 */

public class ImageCompressionUtils {
    /**
     * 质量压缩
     * 设置Bitmap的options属性，降低图片质量，像素不会减少
     *
     * @param bitmap 图片对象
     * @param filePath   压缩后保存图片的位置
     */
    public static void QualityCompressionImage(Bitmap bitmap, String filePath) {
        int options = 10;//0-100,100为不压缩
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
