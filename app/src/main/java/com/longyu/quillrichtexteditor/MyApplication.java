package com.longyu.quillrichtexteditor;

import android.app.Activity;
import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.luck.picture.lib.entity.LocalMedia;

/**
 * @Author: com.longyu
 * @CreateDate: 2021/4/6 15:41
 * @Description:
 */
public class MyApplication extends Application {

    public static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * 判断uri
     *
     * @param activity
     * @param uriContent
     * @return
     */
    public static String returnUri(Activity activity, String uriContent) {
        String myImageUrl = uriContent;
        Uri uri = Uri.parse(myImageUrl);
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        return img_path;
    }

    /**
     * 选择图片地址是否为空
     *
     * @param activity
     * @param media
     * @return
     */
    public static String selectPhotoShow(Activity activity, LocalMedia media) {
        String path = "";
        if (media.isCut() && !media.isCompressed()) {
            // 裁剪过
            path = media.getCutPath();
        } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
            path = media.getCompressPath();
        } else {
            // 原图
            path = media.getPath();
        }
        path = isContent(path)
                && !media.isCut() && !media.isCompressed()
                ? MyApplication.returnUri(activity, path)
                : path;
        return path;
    }

    /**
     * is content://
     *
     * @param url
     * @return
     */
    public static boolean isContent(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return url.startsWith("content://");
    }

}
