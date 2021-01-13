package com.zsp.utilone.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2017/11/15.
 *
 * @author 郑少鹏
 * @desc 动画管理器
 */
public class AnimationManager {
    /**
     * 补间晃动
     *
     * @param cycleTimes 循环次数
     * @param duration   时长
     * @return Animation
     */
    public static @NotNull Animation shake(float cycleTimes, long duration) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 10.0f);
        translateAnimation.setInterpolator(new CycleInterpolator(cycleTimes));
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 属性XY缩放（1-0）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void xyScaleGo(View view, long duration) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 属性XY缩放（0-1）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void xyScaleShow(View view, long duration) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 属性XY缩放（1-0-1）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void xyScale(View view, long duration) {
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 属性X渐变
     *
     * @param view         视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator 插值器
     */
    public static void xGradual(final @NotNull View view, int start, int end, long duration, int interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.width = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 属性Y渐变
     *
     * @param view         视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator 插值器
     */
    public static void yGradual(final @NotNull View view, int start, int end, long duration, int interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 属性Y渐变
     *
     * @param view         视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator 插值器
     * @return ValueAnimator
     */
    public static @NotNull ValueAnimator yGradualReturn(final @NotNull View view, int start, int end, long duration, int interpolator) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = view.getLayoutParams();
        valueAnimator.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            view.setLayoutParams(params);
            view.requestLayout();
        });
        switch (interpolator) {
            case 1:
                valueAnimator.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                valueAnimator.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        valueAnimator.setDuration(duration);
        return valueAnimator;
    }

    /**
     * 属性Y位移
     *
     * @param view            视图
     * @param yTranslationEnd trEnd
     * @param duration        时长
     */
    public static void yTranslation(@NotNull View view, float yTranslationEnd, long duration) {
        float yCurrentTranslation = view.getTranslationY();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", yCurrentTranslation, yTranslationEnd);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * 属性透变（1-0）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void alphaGone(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * 属性透变（0-1）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void alphaShow(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    /**
     * 属性循环透变
     *
     * @param view     视图
     * @param duration 时长
     * @return objectAnimator
     */
    public static @NotNull ObjectAnimator alphaChangeCircle(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.1f);
        // 播放次数（infinite无限重复）
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 播放模式
        // ValueAnimator.RESTART（默）正序重放
        // ValueAnimator.REVERSE倒序回放
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setDuration(duration);
        return objectAnimator;
    }

    /**
     * 属性XY缩透（1-0）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void xyScaleAlphaGone(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 属性XY缩透（0-1）
     *
     * @param view     视图
     * @param duration duration
     */
    public static void xyScaleAlphaShow(View view, long duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        ObjectAnimator xObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator yObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator).with(xObjectAnimator).with(yObjectAnimator);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.setDuration(duration);
        animatorSet.start();
    }

    /**
     * 属性色变
     *
     * @param view       视图
     * @param startColor 开始色
     * @param endColor   终止色
     * @param duration   时长
     */
    public static void colorGradual(final View view, int startColor, int endColor, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        valueAnimator.addUpdateListener(animation -> {
            int color = (int) animation.getAnimatedValue();
            // 取两图层全域（交集色加深）
            view.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    /**
     * 属性旋转
     *
     * @param view     视图
     * @param duration 时长
     * @param start    开始
     * @param end      终止
     */
    public static void rotation(View view, int duration, Float start, Float end) {
        // 负逆正顺（0.0f到360.0f）
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
}
