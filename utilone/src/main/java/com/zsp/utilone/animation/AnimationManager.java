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

/**
 * Created on 2017/11/15.
 *
 * @author 郑少鹏
 * @desc AnimationManager
 */
public class AnimationManager {
    /**
     * 补间晃动
     *
     * @param cycleTimes cycleTimes
     * @param duration   时长
     * @return Animation
     */
    public static Animation shake(float cycleTimes, long duration) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 10.0f);
        translateAnimation.setInterpolator(new CycleInterpolator(cycleTimes));
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    /**
     * 属性XY缩放（1-0-1）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void scaleXY(View view, long duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(duration);
        animSet.start();
    }

    /**
     * 属性XY缩放（1-0）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void scaleXYGo(View view, long duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(duration);
        animSet.start();
    }

    /**
     * 属性XY缩放（0-1）
     *
     * @param view     视图
     * @param duration 时长
     */
    public static void scaleXYShow(View view, long duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 0.0f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 0.0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animatorX).with(animatorY);
        animSet.setDuration(duration);
        animSet.start();
    }

    /**
     * 属性X渐变
     *
     * @param v            视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator interpolator
     */
    public static void gradualX(final View v, int start, int end, long duration, int interpolator) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        va.addUpdateListener(animation -> {
            params.width = (int) animation.getAnimatedValue();
            v.setLayoutParams(params);
            v.requestLayout();
        });
        switch (interpolator) {
            case 1:
                va.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                va.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        va.setDuration(duration);
        va.start();
    }

    /**
     * 属性Y渐变
     *
     * @param v            视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator interpolator
     */
    public static void gradualY(final View v, int start, int end, long duration, int interpolator) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        va.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            v.setLayoutParams(params);
            v.requestLayout();
        });
        switch (interpolator) {
            case 1:
                va.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                va.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        va.setDuration(duration);
        va.start();
    }

    /**
     * 属性Y渐变
     *
     * @param v            视图
     * @param start        开始
     * @param end          终止
     * @param duration     时长
     * @param interpolator interpolator
     * @return ValueAnimator
     */
    public static ValueAnimator gradualYReturn(final View v, int start, int end, long duration, int interpolator) {
        ValueAnimator va = ValueAnimator.ofInt(start, end);
        final ViewGroup.LayoutParams params = v.getLayoutParams();
        va.addUpdateListener(animation -> {
            params.height = (int) animation.getAnimatedValue();
            v.setLayoutParams(params);
            v.requestLayout();
        });
        switch (interpolator) {
            case 1:
                va.setInterpolator(new AccelerateInterpolator(2.0f));
                break;
            case 2:
                va.setInterpolator(new BounceInterpolator());
                break;
            default:
                break;
        }
        va.setDuration(duration);
        return va;
    }

    /**
     * 属性Y位移
     *
     * @param v        视图
     * @param trEnd    trEnd
     * @param duration 时长
     */
    public static void translationY(View v, float trEnd, long duration) {
        float curTranslationY = v.getTranslationY();
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "translationY", curTranslationY, trEnd);
        oa.setDuration(duration);
        oa.start();
    }

    /**
     * 属性透变（1-0）
     *
     * @param v        视图
     * @param duration 时长
     */
    public static void alphaGone(View v, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.0f);
        oa.setDuration(duration);
        oa.start();
    }

    /**
     * 属性透变（0-1）
     *
     * @param v        视图
     * @param duration 时长
     */
    public static void alphaShow(View v, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "alpha", 0.0f, 1.0f);
        oa.setDuration(duration);
        oa.start();
    }

    /**
     * 属性循环透变
     *
     * @param v        视图
     * @param duration 时长
     * @return objectAnimator
     */
    public static ObjectAnimator alphaChangeCircle(View v, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.1f);
        // 播放次数（infinite无限重复）
        oa.setRepeatCount(ValueAnimator.INFINITE);
        // 播放模式
        // ValueAnimator.RESTART（默）正序重放
        // ValueAnimator.REVERSE倒序回放
        oa.setRepeatMode(ValueAnimator.REVERSE);
        oa.setDuration(duration);
        return oa;
    }

    /**
     * 属性XY缩透（1-0）
     *
     * @param v        视图
     * @param duration 时长
     */
    public static void scaleXYAlphaGone(View v, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "alpha", 1.0f, 0.0f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 0.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 0.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(oa).with(animatorX).with(animatorY);
        animSet.setDuration(duration);
        animSet.start();
    }

    /**
     * 属性XY缩透（0-1）
     *
     * @param v        视图
     * @param duration duration
     */
    public static void scaleXYAlphaShow(View v, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(v, "alpha", 0.0f, 1.0f);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(v, "scaleX", 0.0f, 1.0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(v, "scaleY", 0.0f, 1.0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(oa).with(animatorX).with(animatorY);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.setDuration(duration);
        animSet.start();
    }

    /**
     * 属性色变
     *
     * @param v          视图
     * @param startColor 开始色
     * @param endColor   终止色
     * @param duration   时长
     */
    public static void colorGradual(final View v, int startColor, int endColor, int duration) {
        ValueAnimator ca = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);
        ca.addUpdateListener(animation -> {
            int color = (int) animation.getAnimatedValue();
            // 取两图层全域，交集色加深
            v.getBackground().setColorFilter(color, PorterDuff.Mode.DARKEN);
        });
        ca.setDuration(duration);
        ca.start();
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
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation", start, end);
        rotation.setDuration(duration);
        rotation.start();
    }
}
