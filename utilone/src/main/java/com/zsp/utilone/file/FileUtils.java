package com.zsp.utilone.file;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zsp.utilone.url.UrlUtils;

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
import java.text.DecimalFormat;

import timber.log.Timber;
import value.UtilOneMagic;

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

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri String
     * @return Extension including the dot("."); "" if there is no extension; null if uri was null.
     */
    private static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }
        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }

    /**
     * Whether the uri is a media uri.
     *
     * @param uri Uri
     * @return true if uri is a MediaStore uri
     */
    public static boolean isMediaUri(Uri uri) {
        return "media".equalsIgnoreCase(uri.getAuthority());
    }

    /**
     * Convert file into uri.
     *
     * @param file File
     * @return uri
     */
    public static Uri getUri(File file) {
        return (file != null) ? Uri.fromFile(file) : null;
    }

    /**
     * Returns the path only (without file name).
     *
     * @param file File
     * @return File
     */
    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();
                // Construct path without file name.
                String pathWithoutName = filepath.substring(0, filepath.length() - filename.length());
                if (pathWithoutName.endsWith(UtilOneMagic.STRING_BACKSLASH)) {
                    pathWithoutName = pathWithoutName.substring(0, pathWithoutName.length() - 1);
                }
                return new File(pathWithoutName);
            }
        }
        return null;
    }

    /**
     * Get mime type for the give uri.
     *
     * @param file File
     * @return String
     */
    private static String getMimeType(File file) {
        String extension = getExtension(file.getName());
        if (extension.length() > 0) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.substring(1));
        }
        return "application/octet-stream";
    }

    /**
     * Get mime type for the give uri.
     *
     * @param context   Context
     * @param authority YOUR_AUTHORITY.provider
     * @param uri       Uri
     * @return String
     */
    public static String getMimeType(Context context, String authority, Uri uri) {
        String path = getPath(context, authority, uri);
        File file;
        if (path != null) {
            file = new File(path);
            return getMimeType(file);
        } else {
            return null;
        }
    }

    /**
     * Whether the uri authority is local.
     *
     * @param authority YOUR_AUTHORITY.provider
     * @param uri       Uri
     * @return boolean
     */
    private static boolean isLocalStorageDocument(String authority, Uri uri) {
        return authority.equals(uri.getAuthority());
    }

    /**
     * Whether the uri authority is ExternalStorageProvider.
     *
     * @param uri the uri to check
     * @return boolean
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * Whether the uri authority is DownloadsProvider.
     *
     * @param uri the uri to check
     * @return boolean
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * Whether the uri authority is MediaProvider.
     *
     * @param uri the uri to check
     * @return boolean
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Whether the uri authority is Google Photos.
     *
     * @param uri the uri to check
     * @return boolean
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this uri.
     * This is useful for MediaStore uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        final String column = MediaStore.Files.FileColumns.DATA;
        final String[] projection = {column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int columnIndex = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(columnIndex);
            }
        }
        return null;
    }

    /**
     * Get a file path from a uri.
     * <p>
     * This will get the the path for storage access framework documents, as well as the _data field for the MediaStore and other file-based ContentProviders.
     * Callers should check whether the path is local before assuming it represents a local file.
     *
     * @param context   Context
     * @param authority YOUR_AUTHORITY.provider
     * @param uri       the uri to query
     * @return String
     */
    private static String getPath(final Context context, String authority, final Uri uri) {
        Timber.d("Authority: %s +\n Fragment: %s +\n Port: %s +\n Query: %s +\n Scheme: %s +\n Host: %s +\n Segments: %s",
                uri.getAuthority(), uri.getFragment(), uri.getPort(), uri.getQuery(), uri.getScheme(), uri.getHost(), uri.getPathSegments().toString());
        final boolean kitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (kitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            if (isLocalStorageDocument(authority, uri)) {
                // The path is the id
                return DocumentsContract.getDocumentId(uri);
            }
            // ExternalStorageProvider
            else if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if (UtilOneMagic.STRING_PRIMARY.equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                if (id != null && id.startsWith(UtilOneMagic.STRING_RAW_COLON)) {
                    return id.substring(4);
                }
                String[] contentUriPrefixesToTry = new String[]{
                        "content://downloads/public_downloads",
                        "content://downloads/my_downloads"
                };
                for (String contentUriPrefix : contentUriPrefixesToTry) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                    try {
                        String path = getDataColumn(context, contentUri, null, null);
                        if (path != null) {
                            return path;
                        }
                    } catch (Exception e) {
                        Timber.e(e);
                    }
                }
                // path could not be retrieved using ContentResolver, therefore copy file to accessible cache using streams
                String fileName = getFileName(context, authority, uri);
                File cacheDir = getDocumentCacheDir(context);
                File file = generateFileName(fileName, cacheDir);
                String destinationPath = null;
                if (file != null) {
                    destinationPath = file.getAbsolutePath();
                    saveFileFromUri(context, uri, destinationPath);
                }
                return destinationPath;
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if (UtilOneMagic.STRING_IMAGE.equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if (UtilOneMagic.STRING_VIDEO.equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if (UtilOneMagic.STRING_AUDIO.equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if (UtilOneMagic.STRING_CONTENT.equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if (UtilOneMagic.STRING_FILE.equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Convert uri into file, if possible.
     *
     * @param context   Context
     * @param authority YOUR_AUTHORITY.provider
     * @param uri       unsupported or pointed to a remote resource
     * @return File
     */
    public static File getFile(Context context, String authority, Uri uri) {
        if (uri != null) {
            String path = getPath(context, authority, uri);
            if (UrlUtils.isLocal(path)) {
                return new File(path);
            }
        }
        return null;
    }

    /**
     * Get the file size in a human-readable string.
     *
     * @param size 大小
     * @return 大小
     */
    public static String getReadableFileSize(int size) {
        final int bytesInKiloBytes = 1024;
        final DecimalFormat dec = new DecimalFormat("###.#");
        final String kiloBytes = " KB";
        final String megaBytes = " MB";
        final String gigaBytes = " GB";
        float fileSize = 0;
        String suffix = kiloBytes;
        if (size > bytesInKiloBytes) {
            fileSize = Integer.valueOf(size / bytesInKiloBytes).floatValue();
            if (fileSize > bytesInKiloBytes) {
                fileSize = fileSize / bytesInKiloBytes;
                if (fileSize > bytesInKiloBytes) {
                    fileSize = fileSize / bytesInKiloBytes;
                    suffix = gigaBytes;
                } else {
                    suffix = megaBytes;
                }
            }
        }
        return dec.format(fileSize) + suffix;
    }

    private static File getDocumentCacheDir(@NonNull Context context) {
        File dir = new File(context.getCacheDir(), "documents");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Timber.d("cacheDir: %s", context.getCacheDir());
        Timber.d("dir: %s", dir);
        return dir;
    }

    @Nullable
    private static File generateFileName(@Nullable String name, File directory) {
        if (name == null) {
            return null;
        }
        File file = new File(directory, name);
        if (file.exists()) {
            String fileName = name;
            String extension = "";
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex > 0) {
                fileName = name.substring(0, dotIndex);
                extension = name.substring(dotIndex);
            }
            int index = 0;
            while (file.exists()) {
                index++;
                name = fileName + '(' + index + ')' + extension;
                file = new File(directory, name);
            }
        }
        try {
            if (!file.createNewFile()) {
                return null;
            }
        } catch (IOException e) {
            Timber.e(e);
            return null;
        }
        return file;
    }

    private static void saveFileFromUri(Context context, Uri uri, String destinationPath) {
        InputStream inputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            inputStream = context.getContentResolver().openInputStream(uri);
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destinationPath, false));
            byte[] buf = new byte[1024];
            if (inputStream != null) {
                inputStream.read(buf);
                do {
                    bufferedOutputStream.write(buf);
                } while (inputStream.read(buf) != -1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] readBytesFromFile(String filePath) {
        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;
        try {
            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];
            // read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytesArray;
    }

    public static File createTempImageFile(Context context, String fileName) throws IOException {
        // create an image file name
        File storageDir = new File(context.getCacheDir(), "documents");
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    private static String getFileName(@NonNull Context context, String authority, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        String filename = null;
        if (mimeType == null) {
            String path = getPath(context, authority, uri);
            if (path == null) {
                filename = getName(uri.toString());
            } else {
                File file = new File(path);
                filename = file.getName();
            }
        } else {
            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
            if (returnCursor != null) {
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                filename = returnCursor.getString(nameIndex);
                returnCursor.close();
            }
        }
        return filename;
    }

    private static String getName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = filename.lastIndexOf('/');
        return filename.substring(index + 1);
    }
}
