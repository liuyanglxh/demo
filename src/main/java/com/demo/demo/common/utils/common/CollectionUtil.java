package com.demo.demo.common.utils.common;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtil {

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

    /**
     * foreach循环，不用判断非空
     */
    public static<T> void forEach(Collection<? extends T> objs, Consumer<? super T> consumer){
        if (objs != null){
            objs.forEach(consumer);
        }
    }
}
