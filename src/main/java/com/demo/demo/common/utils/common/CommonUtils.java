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

    /**
     * 把一个集合的元素转换成map
     *
     * @param function 需要的key
     */
    public static <T, R> Map<T, R> trans2Map(Collection<? extends R> targets, Function<R, T> function) {
        Map<T, R> result = new HashMap<>();
        if (CollectionUtils.isEmpty(targets)) {
            return result;
        }
        targets.forEach(t -> result.put(function.apply(t), t));
        return result;
    }

}
