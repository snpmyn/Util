package com.zsp.utiltwo.calculation

import android.graphics.PointF
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Created on 2019/8/19.
 * @author 郑少鹏
 * @desc CalculationUtils
 */
object CalculationUtils {
    /**
     * 角度转弧度
     */
    fun degreeToRadian(degree: Float): Float {
        return (degree / 180.0f * PI).toFloat()
    }

    /**
     * 角度sin值
     */
    fun degreeSin(degree: Float): Float {
        return sin(degreeToRadian(degree))
    }

    /**
     * 角度cos值
     */
    fun degreeCos(degree: Float): Float {
        return cos(degreeToRadian(degree))
    }

    /**
     * 点绕原点转一定角后坐标
     */
    fun degreePointF(originalPointF: PointF, resultPointF: PointF, degree: Float) {
        resultPointF.x = originalPointF.x * degreeCos(degree) - originalPointF.y * degreeSin(degree)
        resultPointF.y = originalPointF.x * degreeSin(degree) + originalPointF.y * degreeCos(degree)
    }
}