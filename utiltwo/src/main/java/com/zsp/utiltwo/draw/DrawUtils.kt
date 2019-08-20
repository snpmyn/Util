package com.zsp.utiltwo.draw

import android.graphics.Paint

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc DrawUtils
 */
object DrawUtils {
    /**
     * 绘文本时X轴上垂直居中Y坐标
     */
    fun Paint.getCenteredY(): Float {
        return this.fontSpacing / 2 - this.fontMetrics.bottom
    }

    /**
     * 绘文本时X轴上贴紧X轴上边缘Y坐标
     */
    fun Paint.getBottomedY(): Float {
        return -this.fontMetrics.bottom
    }

    /**
     * 绘文本时X轴上贴近X轴下边缘Y坐标
     */
    fun Paint.getToppedY(): Float {
        return -this.fontMetrics.ascent
    }
}