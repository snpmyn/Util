package com.zsp.utilone.map;

import com.zsp.utilone.data.IntUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2019/9/6.
 *
 * @author 郑少鹏
 * @desc MapUtils
 */
public class MapUtils {
    /**
     * Map自List
     * <p>
     * List示例：
     * keyOne, valueOne, keyTwo, valueTwo ...
     *
     * @param list List
     * @return Map
     */
    public static @NotNull Map<String, String> mapFromList(@NotNull List<String> list) {
        Map<String, String> map = new HashMap<>(list.size() / 2);
        for (int i = 0; i < list.size(); i++) {
            if (IntUtils.even(i)) {
                map.put(list.get(i), list.get(i + 1));
            }
        }
        return map;
    }
}
