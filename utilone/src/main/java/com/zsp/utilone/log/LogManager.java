package com.zsp.utilone.log;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.zsp.application.UtilApplication;
import com.zsp.utilone.thread.ThreadManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created on 2017/12/28.
 *
 * @author 郑少鹏
 * @desc LogManager
 */
public class LogManager {
    static final int V = Log.VERBOSE;
    static final int D = Log.DEBUG;
    static final int I = Log.INFO;
    static final int W = Log.WARN;
    static final int E = Log.ERROR;
    static final int A = Log.ASSERT;
    /**
     * magic value
     */
    private static final String JSON_ONE = "{";
    private static final String JSON_TWO = "[";
    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};
    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML = 0x30;
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String TOP_CORNER = "┌";
    private static final String MIDDLE_CORNER = "├";
    private static final String LEFT_BORDER = "│ ";
    private static final String BOTTOM_CORNER = "└";
    private static final String SIDE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final int MAX_LEN = 4000;
    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String ARGS = "args";
    private static final Config CONFIG = new Config();
    private static ScheduledExecutorService scheduledExecutorService;
    /**
     * log默存目录
     */
    private static String sDefaultDir;
    /**
     * log存目录
     */
    private static String sDir;
    /**
     * log文件前缀
     */
    private static String sFilePrefix = "util";
    /**
     * log总开关（默开）
     */
    private static boolean sLogSwitch = true;
    /**
     * logcat打印（默打）
     */
    private static boolean sLog2ConsoleSwitch = true;
    /**
     * log标签
     */
    private static String sGlobalTag = null;
    /**
     * log标签空白
     */
    private static boolean sTagIsSpace = true;
    /**
     * log头部（默开）
     */
    private static boolean sLogHeadSwitch = true;
    /**
     * log写入文件（默关）
     */
    private static boolean sLog2FileSwitch = false;
    /**
     * log边框（默开）
     */
    private static boolean sLogBorderSwitch = true;
    /**
     * log控制台过滤器
     */
    private static int sConsoleFilter = V;
    /**
     * log文件过滤器
     */
    private static int sFileFilter = V;
    /**
     * log栈深度
     */
    private static int sStackDeep = 1;

    private LogManager() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Config getConfig() {
        return CONFIG;
    }

    public static void v(final Object... contents) {
        log(V, sGlobalTag, contents);
    }

    public static void vTag(final String tag, final Object... contents) {
        log(V, tag, contents);
    }

    public static void d(final Object... contents) {
        log(D, sGlobalTag, contents);
    }

    public static void dTag(final String tag, final Object... contents) {
        log(D, tag, contents);
    }

    public static void i(final Object... contents) {
        log(I, sGlobalTag, contents);
    }

    public static void iTag(final String tag, final Object... contents) {
        log(I, tag, contents);
    }

    public static void w(final Object... contents) {
        log(W, sGlobalTag, contents);
    }

    public static void wTag(final String tag, final Object... contents) {
        log(W, tag, contents);
    }

    public static void e(final Object... contents) {
        log(E, sGlobalTag, contents);
    }

    public static void eTag(final String tag, final Object... contents) {
        log(E, tag, contents);
    }

    public static void a(final Object... contents) {
        log(A, sGlobalTag, contents);
    }

    public static void aTag(final String tag, final Object... contents) {
        log(A, tag, contents);
    }

    public static void file(final Object content) {
        log(FILE | D, sGlobalTag, content);
    }

    public static void file(@TYPE final int type, final Object content) {
        log(FILE | type, sGlobalTag, content);
    }

    public static void file(final String tag, final Object content) {
        log(FILE | D, tag, content);
    }

    public static void file(@TYPE final int type, final String tag, final Object content) {
        log(FILE | type, tag, content);
    }

    public static void json(final String content) {
        log(JSON | D, sGlobalTag, content);
    }

    public static void json(@TYPE final int type, final String content) {
        log(JSON | type, sGlobalTag, content);
    }

    public static void json(final String tag, final String content) {
        log(JSON | D, tag, content);
    }

    public static void json(@TYPE final int type, final String tag, final String content) {
        log(JSON | type, tag, content);
    }

    public static void xml(final String content) {
        log(XML | D, sGlobalTag, content);
    }

    public static void xml(@TYPE final int type, final String content) {
        log(XML | type, sGlobalTag, content);
    }

    public static void xml(final String tag, final String content) {
        log(XML | D, tag, content);
    }

    public static void xml(@TYPE final int type, final String tag, final String content) {
        log(XML | type, tag, content);
    }

    private static void log(final int type, final String tag, final Object... contents) {
        boolean flag2 = (!sLogSwitch || (!sLog2ConsoleSwitch && !sLog2FileSwitch));
        if (flag2) {
            return;
        }
        int typeLow = type & 0x0f, typeHigh = type & 0xf0;
        if (typeLow < sConsoleFilter && typeLow < sFileFilter) {
            return;
        }
        final TagHead tagHead = processTagAndHead(tag);
        String body = processBody(typeHigh, contents);
        if (sLog2ConsoleSwitch && typeLow >= sConsoleFilter && typeHigh != FILE) {
            print2Console(typeLow, tagHead.tag, tagHead.consoleHead, body);
        }
        boolean flag1 = (sLog2FileSwitch || typeHigh == FILE);
        if (flag1 && typeLow >= sFileFilter) {
            print2File(typeLow, tagHead.tag, tagHead.fileHead + body);
        }
    }

    private static TagHead processTagAndHead(String tag) {
        if (!sTagIsSpace && !sLogHeadSwitch) {
            tag = sGlobalTag;
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            StackTraceElement targetElement = stackTrace[3];
            String fileName = targetElement.getFileName();
            String className;
            if (fileName == null) {
                // 混淆或致获空 加-keepattributes SourceFile, LineNumberTable
                className = targetElement.getClassName();
                String[] classNameInfo = className.split("\\.");
                if (classNameInfo.length > 0) {
                    className = classNameInfo[classNameInfo.length - 1];
                }
                int index = className.indexOf('$');
                if (index != -1) {
                    className = className.substring(0, index);
                }
                fileName = className + ".java";
            } else {
                // 混淆或致文件名被改而找不到"."
                int index = fileName.indexOf('.');
                className = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (sTagIsSpace) {
                tag = isSpace(tag) ? className : tag;
            }
            if (sLogHeadSwitch) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter().format("%s, %s(%s:%d)", tName, targetElement.getMethodName(), fileName, targetElement.getLineNumber()).toString();
                final String fileHead = " [" + head + "]: ";
                if (sStackDeep <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead = new String[Math.min(sStackDeep, stackTrace.length - 3)];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + 3];
                        consoleHead[i] = new Formatter().format("%s%s(%s:%d)", space, targetElement.getMethodName(), targetElement.getLineNumber()).toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String processBody(final int type, final Object... contents) {
        String body = NULL;
        if (contents != null) {
            if (contents.length == 1) {
                Object object = contents[0];
                if (object != null) {
                    body = object.toString();
                }
                if (type == JSON) {
                    body = formatJson(body);
                } else if (type == XML) {
                    body = formatXml(body);
                }
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append(ARGS).append("[").append(i).append("]").append(" = ").append(content == null ? NULL : content.toString()).append(LINE_SEP);
                }
                body = sb.toString();
            }
        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith(JSON_ONE)) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith(JSON_TWO)) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static void print2Console(final int type, final String tag, final String[] head, final String msg) {
        printBorder(type, tag, true);
        printHead(type, tag, head);
        printMsg(type, tag, msg);
        printBorder(type, tag, false);
    }

    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (sLogBorderSwitch) {
            Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                Log.println(type, tag, sLogBorderSwitch ? LEFT_BORDER + aHead : aHead);
            }
            if (sLogBorderSwitch) {
                Log.println(type, tag, MIDDLE_BORDER);
            }
        }
    }

    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!sLogBorderSwitch) {
            Log.println(type, tag, msg);
            return;
        }
        String[] lines = new String[0];
        if (LINE_SEP != null) {
            lines = msg.split(LINE_SEP);
        }
        for (String line : lines) {
            Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void print2File(final int type, final String tag, final String msg) {
        final Format formatOriginal = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ", Locale.getDefault());
        Date now = new Date(System.currentTimeMillis());
        String format = formatOriginal.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        final String fullPath = (sDir == null ? sDefaultDir : sDir) + sFilePrefix + "-" + date + ".txt";
        if (!createOrExistsFile(fullPath)) {
            Log.e(tag, "log to " + fullPath + " failed!");
            return;
        }
        final String content = time + T[type - V] + "/" + tag + msg + LINE_SEP;
        if (input2File(content, fullPath)) {
            Log.d(tag, "log to " + fullPath + " success!");
        } else {
            Log.e(tag, "log to " + fullPath + " failed!");
        }
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                printDeviceInfo(filePath);
            }
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = UtilApplication.getInstance().getPackageManager().getPackageInfo(UtilApplication.getInstance().getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String head = "************* Log Head *************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商
                "\nDevice Model       : " + Build.MODEL +// 设备型号
                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK版
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head *************\n\n";
        input2File(head, filePath);
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean input2File(final String input, final String filePath) {
        if (scheduledExecutorService == null) {
            scheduledExecutorService = ThreadManager.stepScheduledExecutorService();
        }
        Future<Boolean> submit = scheduledExecutorService.submit(() -> {
            BufferedWriter bw = null;
            try {
                bw = new BufferedWriter(new FileWriter(filePath, true));
                bw.write(input);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            return submit.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    private @interface TYPE {

    }

    public static class Config {
        private Config() {
            if (sDefaultDir != null) {
                return;
            }
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && UtilApplication.getInstance().getExternalCacheDir() != null) {
                sDefaultDir = UtilApplication.getInstance().getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            } else {
                sDefaultDir = UtilApplication.getInstance().getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        public Config setLogSwitch(final boolean logSwitch) {
            sLogSwitch = logSwitch;
            return this;
        }

        public Config setConsoleSwitch(final boolean consoleSwitch) {
            sLog2ConsoleSwitch = consoleSwitch;
            return this;
        }

        public Config setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                sGlobalTag = "";
                sTagIsSpace = true;
            } else {
                sGlobalTag = tag;
                sTagIsSpace = false;
            }
            return this;
        }

        public Config setLogHeadSwitch(final boolean logHeadSwitch) {
            sLogHeadSwitch = logHeadSwitch;
            return this;
        }

        public Config setLog2FileSwitch(final boolean log2FileSwitch) {
            sLog2FileSwitch = log2FileSwitch;
            return this;
        }

        public Config setDir(final String dir) {
            if (isSpace(dir)) {
                sDir = null;
            } else {
                if (FILE_SEP != null) {
                    sDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
                }
            }
            return this;
        }

        public Config setDir(final File dir) {
            sDir = dir == null ? null : dir.getAbsolutePath() + FILE_SEP;
            return this;
        }

        public Config setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                sFilePrefix = "util";
            } else {
                sFilePrefix = filePrefix;
            }
            return this;
        }

        public Config setBorderSwitch(final boolean borderSwitch) {
            sLogBorderSwitch = borderSwitch;
            return this;
        }

        public Config setConsoleFilter(@TYPE final int consoleFilter) {
            sConsoleFilter = consoleFilter;
            return this;
        }

        public Config setFileFilter(@TYPE final int fileFilter) {
            sFileFilter = fileFilter;
            return this;
        }

        public void setStackDeep(@IntRange(from = 1) final int stackDeep) {
            sStackDeep = stackDeep;
        }

        @NonNull
        @Override
        public String toString() {
            return "switch: " + sLogSwitch
                    + LINE_SEP + "console: " + sLog2ConsoleSwitch
                    + LINE_SEP + "tag: " + (sTagIsSpace ? "null" : sGlobalTag)
                    + LINE_SEP + "head: " + sLogHeadSwitch
                    + LINE_SEP + "file: " + sLog2FileSwitch
                    + LINE_SEP + "dir: " + (sDir == null ? sDefaultDir : sDir)
                    + LINE_SEP + "filePrefix" + sFilePrefix
                    + LINE_SEP + "border: " + sLogBorderSwitch
                    + LINE_SEP + "consoleFilter: " + T[sConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[sFileFilter - V]
                    + LINE_SEP + "stackDeep: " + sStackDeep;
        }
    }

    private static class TagHead {
        String tag;
        String[] consoleHead;
        String fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }
}

