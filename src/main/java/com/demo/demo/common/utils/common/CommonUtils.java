package com.demo.demo.common.utils.common;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 通用工具类
 */
public class CommonUtils {

    /**
     * 判断是否全为null或者空字符串
     */
    public static boolean allEmpty(Collection<String> strs) {
        for (String str : strs) {
            if (!StringUtils.isEmpty(str)) {
                return false;
            }
        }
        return true;
    }



}
