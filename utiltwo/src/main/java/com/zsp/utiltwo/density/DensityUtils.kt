package com.zsp.utiltwo.density

import android.content.Context

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc DensityUtils
 */
object DensityUtils {
    /**
     * 设备独立像素转像素
     */
    fun dipToPxByFloat(context: Context, dipValue: Float): Int {
        if (dipValue == 0.0f) return 0
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 像素转设备独立像素
     */
    fun pxToDipByInt(context: Context, pxValue: Int): Float {
        if (pxValue == 0) return 0.0f
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f)
    }
}