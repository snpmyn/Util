package com.zsp.utiltwo.assist

import android.graphics.Canvas
import android.graphics.Color

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc AssistUtils
 */
object AssistUtils {
    /**
     * 绿幕背景
     */
    fun greenCurtainBackground(canvas: Canvas, debug: Boolean) {
        if (debug) {
            canvas.drawColor(Color.GREEN)
        }
    }
}