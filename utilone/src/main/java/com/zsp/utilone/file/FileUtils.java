package com.zsp.utilone.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

/**
 * Created on 2017/12/6.
 *
 * @author 郑少鹏
 * @desc FileUtils
 */
public class FileUtils {
    /**
     * 创文件夹
     *
     * @param folderPath  文件夹路径
     * @param singleLevel 单级否
     */
    public static void createFolder(String folderPath, boolean singleLevel) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            // mkdirs()建多级文件夹，mkdir()建一级文件夹
            if (singleLevel) {
                folder.mkdir();
            } else {
                folder.mkdirs();
            }
        }
    }

    /**
     * 据uri获真路径
     *
     * @param context    上下文
     * @param contentUri contentUri
     * @return 据URI真路径
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String[] pro = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(contentUri, pro, null, null, null)) {
            int columnIndex;
            if (cursor != null) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(columnIndex);
            } else {
                return null;
            }
        }
    }

    /**
     * 据路径文件存否
     *
     * @param filePath filePath
     * @return 据路径文件存否
     */
    public static boolean isFileExistByPath(final String filePath) {
        return isFileExist(getFileByPath(filePath));
    }

    /**
     * 文件存否
     *
     * @param file file
     * @return 文件存否
     */
    private static boolean isFileExist(final File file) {
        return file != null && file.exists();
    }

    /**
     * 据文件路径获文件
     *
     * @param filePath filePath
     * @return 文件
     */
    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
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

    /**
     * 删文件或文件夹
     *
     * @param fileName 所删文件名
     * @return 成true败false
     */
    public static boolean deleteByName(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return deleteByNameTwo(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删单文件
     *
     * @param fileName 所删文件名
     * @return 成true败false
     */
    private static boolean deleteByNameTwo(String fileName) {
        File file = new File(fileName);
        // 路径对应文件存在且是文件则直删
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删单文件" + fileName + "成功");
                return true;
            } else {
                System.out.println("删单文件" + fileName + "失败");
                return false;
            }
        } else {
            System.out.println("删单文件失败：" + fileName + "不存在");
            return false;
        }
    }

    /**
     * 删目录及目录下文件
     *
     * @param dir 所删目录文件路径
     * @return 成true败false
     */
    private static boolean deleteDirectory(String dir) {
        // dir不以文件分隔符结尾则自动添文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // dir对应文件不存或非目录则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Timber.d("删目录失败：%s%s", dir, "不存在");
            return false;
        }
        boolean flag = true;
        // 删文件夹所有文件（含子目录）
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                // 删子文件
                if (file.isFile()) {
                    flag = deleteByNameTwo(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
                // 删子目录
                else if (file.isDirectory()) {
                    flag = deleteDirectory(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        if (!flag) {
            Timber.d("删目录失败");
            return false;
        }
        // 删当前目录
        if (dirFile.delete()) {
            Timber.d("删目录%s%s", dir, "成功");
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删文件或文件夹
     *
     * @param file 文件
     */
    public static void deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteFile(f);
                            } else {
                                f.delete();
                            }
                        }
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 据输入流存文件
     *
     * @param file        文件
     * @param inputStream 输入流
     * @return 文件
     */
    public static boolean writeFile(File file, InputStream inputStream) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int length;
            while ((length = inputStream.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            return true;
        } catch (Exception e) {
            if (file != null && file.exists()) {
                file.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(os);
            closeStream(inputStream);
        }
        return false;
    }

    /**
     * 关流
     *
     * @param closeable closeable
     */
    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件转字符串
     *
     * @param filePath 文件地址
     * @return 字符串
     */
    public static StringBuilder fileToString(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String s;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator()).append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 拷贝
     *
     * @param newFile  新文件
     * @param destFile 目标文件
     * @return 成true败false
     * @throws IOException 输入/出流异常
     */
    public static boolean copy(File newFile, File destFile) throws IOException {
        if (newFile != null && destFile != null) {
            if (!newFile.exists()) {
                return false;
            } else {
                BufferedInputStream var2 = null;
                BufferedOutputStream var3 = null;
                try {
                    String var4 = destFile.getAbsolutePath();
                    String var5 = var4.substring(0, var4.lastIndexOf(File.separator));
                    File var6 = new File(var5);
                    if (!var6.exists()) {
                        var6.mkdirs();
                    }
                    var2 = new BufferedInputStream(new FileInputStream(newFile));
                    var3 = new BufferedOutputStream(new FileOutputStream(destFile));
                    byte[] var7 = new byte[5120];
                    int var8;
                    while ((var8 = var2.read(var7)) != -1) {
                        var3.write(var7, 0, var8);
                    }
                    var3.flush();
                    return true;
                } finally {
                    if (var2 != null) {
                        try {
                            var2.close();
                        } catch (IOException var19) {
                            var19.printStackTrace();
                        }
                    }
                    if (var3 != null) {
                        try {
                            var3.close();
                        } catch (IOException var18) {
                            var18.printStackTrace();
                        }
                    }
                }
            }
        } else {
            return false;
        }
    }
}
