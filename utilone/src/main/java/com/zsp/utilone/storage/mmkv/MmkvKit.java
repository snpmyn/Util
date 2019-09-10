package com.zsp.utilone.storage.mmkv;

import com.tencent.mmkv.MMKV;

/**
 * Created on 2019/9/9.
 *
 * @author 郑少鹏
 * @desc MmkvKit
 */
public class MmkvKit {
    public static MMKV defaultMmkv() {
        return MMKV.defaultMMKV();
    }
}
