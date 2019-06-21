package com.zsp.utilone.signature;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created on 2019/6/21.
 *
 * @author 郑少鹏
 * @desc SignatureUtils
 */
public class SignatureUtils {
    /**
     * jar签名
     *
     * @param path 路径
     * @return jar签名
     */
    public static String jarSignature(String path) {
        String var1 = "";
        try {
            if (TextUtils.isEmpty(path)) {
                return var1;
            }
            File var2 = new File(path);
            if (!var2.exists()) {
                return var1;
            }
            JarFile var3 = new JarFile(var2);
            Enumeration var4 = var3.entries();
            while (var4.hasMoreElements()) {
                JarEntry var5 = (JarEntry) var4.nextElement();
                InputStream var6 = var3.getInputStream(var5);
                byte[] var7 = new byte[10240];
                while (-1 != var6.read(var7)) {
                    Log.e("jarSignature", "-1 != var6.read(var7)");
                }
                Certificate[] var8 = var5.getCertificates();
                if (var8 != null && var8.length > 0) {
                    byte var11 = 0;
                    Certificate var12 = var8[var11];
                    MessageDigest var13 = MessageDigest.getInstance("SHA-1");
                    var1 = bytesToHexString(var13.digest(var12.getEncoded()));
                    return var1;
                }
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }
        return var1;
    }

    private static String bytesToHexString(byte[] bytes) {
        if (null == bytes) {
            return "";
        } else {
            int var2 = bytes.length;
            StringBuffer var3 = new StringBuffer(var2 * 2);
            Formatter var4 = new Formatter(var3);
            for (byte aByte : bytes) {
                var4.format("%02x", aByte);
            }
            var4.close();
            return var3.toString().toUpperCase();
        }
    }
}
