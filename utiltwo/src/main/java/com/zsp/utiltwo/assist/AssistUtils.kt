package com.zsp.utiltwo.assist

import android.graphics.Canvas
import android.graphics.Color

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc AssistUtils
 */
/**
 * 绿幕背景
 */
internal fun Canvas.greenCurtainBackground(debug: Boolean) {
    if (debug) {
        this.drawColor(Color.GREEN)
    }
}