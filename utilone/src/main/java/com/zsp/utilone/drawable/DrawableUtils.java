package com.zsp.utilone.drawable;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created on 2019/6/21.
 *
 * @author 郑少鹏
 * @desc DrawableUtils
 */
public class DrawableUtils {
    /**
     * 据URL获Drawable
     * <p>
     * Required thread execution.
     * Main thread cannot get graph.
     *
     * @param imageUrl  图片统一资源定位符
     * @param imageName 图片名
     * @return Drawable
     */
    public static Drawable getDrawableByUrl(String imageUrl, String imageName) {
        Drawable drawable = null;
        try {
            URL url = new URL(imageUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            InputStream inputStream;
            inputStream = urlConnection.getInputStream();
            drawable = Drawable.createFromStream(inputStream, imageName + ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }
}
