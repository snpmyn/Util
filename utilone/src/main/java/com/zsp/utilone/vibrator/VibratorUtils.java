package com.zsp.utilone.vibrator;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created on 2019/8/31.
 *
 * @author 郑少鹏
 * @desc VibratorUtils
 */
public class VibratorUtils {
    /**
     * 一次振动
     *
     * @param context      上下文
     * @param milliseconds 振动时长（ms）
     * @param amplitude    振动强度（1到255间或DEFAULT_AMPLITUDE）
     */
    public static void oneShotVibration(@NotNull Context context, long milliseconds, int amplitude) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect vibrationEffect = VibrationEffect.createOneShot(milliseconds, amplitude);
                vibrator.vibrate(vibrationEffect);
            }
            vibrator.vibrate(200);
        }
    }

    /**
     * 波形振动
     *
     * @param context    上下文
     * @param timings    交替开关定时模式（从关闭开始，0忽略时序/幅度序列）
     * @param amplitudes 振幅值（0（断开）到255间或DEFAULT_AMPLITUDE）
     * @param repeat     振动重复模式（-1不重复、0一直重复、1从数组下标1开始重复振动后结束、2从数组下标2开始重复振动后结束）
     */
    public static void waveformVibration(@NotNull Context context, long[] timings, int[] amplitudes, int repeat) {
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, repeat);
                vibrator.vibrate(vibrationEffect);
            }
            vibrator.vibrate(timings, repeat);
        }
    }
}
