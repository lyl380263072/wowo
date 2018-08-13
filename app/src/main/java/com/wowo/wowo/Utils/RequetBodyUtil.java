package com.wowo.wowo.Utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author: ZhangXiaoWei
 * email : cherno@126.com
 * blog  : http://www.jianshu.com/users/0f532bf88454/latest_articles
 * time  : 2017/12/21
 * desc  :
 */

public class RequetBodyUtil {
    public static RequestBody toRequestBodyOfText (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }

    public static RequestBody toRequestBodyOfImage(File pFile){
        RequestBody fileBody = RequestBody.create(MediaType.parse("Multipart/form-data"), pFile);
        return fileBody;
    }

    public static MultipartBody.Part toMultiPartOfRequestBody(String key,RequestBody body,File file){
        MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), body);
        return part;
    }
}
