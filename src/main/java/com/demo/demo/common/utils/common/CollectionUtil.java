package com.demo.demo.common.utils.common;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtil {

    /**
     * 把一个集合的元素转换成map
     *
     * @param function 需要的key
     */
    public static <T, R> Map<T, R> getMap(Collection<? extends R> targets, Function<R, T> function) {
        Objects.requireNonNull(function);
        Map<T, R> result = new HashMap<>();
        if (CollectionUtils.isEmpty(targets)) {
            return result;
        }
        targets.forEach(t -> result.put(function.apply(t), t));
        return result;
    }

    public static <T> void forEach(Collection<? extends T> objs, Consumer<? super T> consumer) {
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

    /**
     * 将扁平化数据结构化
     *
     * @param topParent    顶部元素的父标识
     * @param targets      数据
     * @param idFunc       获取元素标识的方法
     * @param parentIdFunc 获取父元素标识的方法
     * @param setSubFunc   设置子元素的方法
     */
    public static <T, R> List<T> buildTree(R topParent, Collection<T> targets,
                                           Function<T, R> idFunc, Function<T, R> parentIdFunc,
                                           BiConsumer<T, List<T>> setSubFunc) {
        if (CollectionUtils.isEmpty(targets)) {
            return Collections.emptyList();
        }

        Map<R, List<T>> map  = new HashMap<>();
        targets.forEach(target -> {
            R p = parentIdFunc.apply(target);
            map.putIfAbsent(p, new ArrayList<>());
            map.get(p).add(target);
        });

        List<T> result = map.get(topParent);
        if (result != null) {
            buildTree(result, map, idFunc, setSubFunc);
            return result;
        }
        return Collections.emptyList();
    }

    private static <T, R> void buildTree(Collection<T> targets, Map<R, List<T>> map,
                                         Function<T, R> idFunc, BiConsumer<T, List<T>> setSubFunc) {
        targets.forEach(t -> {
            List<T> subs = map.get(idFunc.apply(t));
            if (subs != null) {
                setSubFunc.accept(t, subs);
                buildTree(subs, map, idFunc, setSubFunc);
            }
        });
    }

}
