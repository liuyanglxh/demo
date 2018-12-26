package com.demo.demo.common.utils.common;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtil {

    /**
     * 把一个集合的元素转换成map
     *
     * @param function 需要的key
     */
    public static <T, R> Map<T, R> trans2Map(Collection<? extends R> targets, Function<R, T> function) {
        Objects.requireNonNull(function);
        Map<T, R> result = new HashMap<>();
        if (CollectionUtils.isEmpty(targets)) {
            return result;
        }
        targets.forEach(t -> result.put(function.apply(t), t));
        return result;
    }

    public static<T> void forEach(Collection<? extends T> objs, Consumer<? super T> consumer){
        Objects.requireNonNull(consumer);
        if (!CollectionUtils.isEmpty(objs)) {
            objs.forEach(consumer);
        }
    }

    public static <K, V> void forEach(Map<K, V> map, BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        if (map != null && map.size() > 0) {
            map.forEach(action);
        }
    }
}
