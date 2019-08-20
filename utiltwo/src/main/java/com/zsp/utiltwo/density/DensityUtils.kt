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
    internal fun dipToPxByFloat(context: Context, dpValue: Float): Float {
        if (dpValue == 0.0f) return 0.0f
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f)
    }
}