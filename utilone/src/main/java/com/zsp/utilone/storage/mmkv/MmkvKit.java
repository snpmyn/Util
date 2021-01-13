package com.zsp.utilone.storage.mmkv;

import com.tencent.mmkv.MMKV;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2019/9/9.
 *
 * @author 郑少鹏
 * @desc MmkvKit
 */
public class MmkvKit {
    public static @NotNull MMKV defaultMmkv() {
        return MMKV.defaultMMKV();
    }
}
