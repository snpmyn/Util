package com.zsp.utilone.mediaplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created on 2019/2/28.
 *
 * @author 郑少鹏
 * @desc MediaPlayerUtils
 */
public class MediaPlayerUtils {
    /**
     * 播放Raw资源
     *
     * @param context 上下文
     * @param resId   资源ID
     */
    public static void playRawResource(Context context, int resId) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = context.getResources().openRawResourceFd(resId);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放路径资源
     *
     * @param path 路径
     */
    public static void playStringResource(String path) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放Assets资源
     *
     * @param context  上下文
     * @param fileName 文件名
     */
    private void playAssetsResource(Context context, String fileName) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            // 准备就绪状态监听
            mediaPlayer.setOnPreparedListener(mp -> {
                // 开始播放
                mediaPlayer.start();
            });
            // 准备播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
