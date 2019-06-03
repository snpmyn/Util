package com.zsp.utilone.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created on 2017/10/16.
 *
 * @author 郑少鹏
 * @desc Error级别日志打印工具，带定位功能及输出调用处类信息（类名、方法名、行等），点可追踪。
 */
public class ErrorLogManager {
    /**
     * debug否
     */
    private static boolean isDebug = true;

    /**
     * 打错误信息
     *
     * @param msg 信息
     */
    public static void e(Object msg) {
        if (!isDebug) {
            return;
        }
        printMsg(getStackTrace(), "", msg.toString());
    }

    /**
     * 打错误信息
     *
     * @param tag 标志
     * @param msg 信息
     */
    public static void e(String tag, Object msg) {
        if (!isDebug) {
            return;
        }
        printMsg(getStackTrace(), tag, msg.toString());
    }

    /**
     * 打错误信息
     *
     * @param tag       标志
     * @param msg       信息
     * @param throwable 可抛
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (!isDebug) {
            return;
        }
        Log.e(tag, msg, throwable);
    }

    /**
     * StackTraceElement对象
     * <p>
     * 当前调LogManager.e()处类信息（类名、方法名、行等）。
     *
     * @return StackTraceElement
     */
    private static StackTraceElement getStackTrace() {
        return Thread.currentThread().getStackTrace()[4];
    }

    /**
     * debug否
     *
     * @param isDebug debug否
     */
    public static void setDebug(boolean isDebug) {
        ErrorLogManager.isDebug = isDebug;
    }

    /**
     * 打日志
     *
     * @param element 当前调LogManager.e()处类信息（类名、方法名、行等）
     * @param tag     标志
     * @param msg     信息
     */
    private static void printMsg(StackTraceElement element, String tag, String msg) {
        // 替()为中文（防冲突）
        msg = msg.replace("(", "（").replace(")", "）");
        StringBuilder sb = new StringBuilder();
        // 暂无需类名
        /*String className = traceElement.getClassName();*/
        String fileName = element.getFileName();
        sb.append(element.getMethodName())
                .append("(").append(fileName).append(":")
                .append(element.getLineNumber())
                .append(")");
        String msgT = (TextUtils.isEmpty(msg) ? "" : (msg + " -> ")) + sb.toString();
        Log.e(tag, msgT);
    }
}
