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
     * Flags基本操作
     * FlagSet添Flag
     */
    fun Int.addFlag(flag: Int): Int {
        return this or flag
    }

    /**
     * Flags基本操作
     * FlagSet移Flag
     */
    fun Int.removeFlag(flag: Int): Int {
        return this and (flag.inv())
    }

    /**
     * Flags基本操作
     * FlagSet含Flag否
     */
    fun Int.containFlag(flag: Int): Boolean {
        return this or flag == this
    }

    /**
     * 角度制转弧度制
     */
    fun Float.degree2radian(): Float {
        return (this / 180f * PI).toFloat()
    }

    /**
     * 角度sin值
     */
    fun Float.degreeSin(): Float {
        return sin(this.degree2radian())
    }

    /**
     * 角度cos值
     */
    fun Float.degreeCos(): Float {
        return cos(this.degree2radian())
    }

    /**
     * 点绕原点转一定角后坐标
     */
    fun PointF.degreePointF(outPointF: PointF, degree: Float) {
        outPointF.x = this.x * degree.degreeCos() - this.y * degree.degreeSin()
        outPointF.y = this.x * degree.degreeSin() + this.y * degree.degreeCos()
    }
}