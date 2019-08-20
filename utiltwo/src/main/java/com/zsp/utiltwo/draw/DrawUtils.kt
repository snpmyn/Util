@file:JvmName("DrawUtils")

package com.zsp.utiltwo.draw

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.annotation.ColorInt

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc DrawUtils
 */
/**
 * 创画笔
 */
@JvmOverloads
internal fun <T : View> T.createPaint(colorString: String? = null, @ColorInt color: Int? = null): Paint {
    return Paint().apply {
        this.paintReset(colorString, color)
    }
}

/**
 * 画笔重置
 */
@JvmOverloads
internal fun Paint.paintReset(colorString: String? = null, @ColorInt color: Int? = null) {
    this.reset()
    // 默白色（处理系统渲染抗锯齿时人眼观察到像素色）
    this.color = color ?: Color.parseColor(colorString ?: "#FFFFFF")
    this.isAntiAlias = true
    this.style = Paint.Style.FILL
    this.strokeWidth = 0f
}

/**
 * 绘文本时X轴上垂直居中Y坐标
 */
internal fun Paint.getCenteredY(): Float {
    return this.fontSpacing / 2 - this.fontMetrics.bottom
}

/**
 * 绘文本时X轴上贴紧X轴上边缘Y坐标
 */
internal fun Paint.getBottomedY(): Float {
    return -this.fontMetrics.bottom
}

/**
 * 绘文本时X轴上贴近X轴下边缘Y坐标
 */
internal fun Paint.getToppedY(): Float {
    return -this.fontMetrics.ascent
}