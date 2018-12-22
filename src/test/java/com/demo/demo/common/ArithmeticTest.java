package com.demo.demo.common;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by liuyang on 2018/12/4
 */
public class ArithmeticTest {

    @Test
    public void test1() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> list2 = Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
        System.out.println(this.getMiddle(list1, list2));
    }

    private Integer getMiddle(List<Integer> list1, List<Integer> list2) {
        if (list1 == null || list2 == null) {
            throw new RuntimeException();
        }

        //确定位置
        int m = list1.size();
        int n = list2.size();
        int total = m + n;
        Integer index;//目标位置左右的数据个数

        if (total % 2 == 0) {
            index = total / 2 - 1;
        } else {
            index = total / 2;
        }


        return this.merge(index, list1, list2);
    }

    /**
     * 合并
     */
    private Integer merge(Integer index, List<Integer> list1, List<Integer> list2) {
        //简单情况，一个数组最大值小于等于另一个数组最小值
        List<Integer> big = null;
        List<Integer> small = null;
        if (list1.get(list1.size() - 1) >= list2.get(list2.size() - 1)) {
            big = list1;
            small = list2;
        } else if (list2.get(list2.size() - 1) >= list1.get(list1.size() - 1)) {
            big = list2;
            small = list1;
        }
        if (big != null) {
            List<Integer> result = new ArrayList<>();
            result.addAll(small);
            result.addAll(big);
            if (result.size() % 2 == 0) {
                return (result.get(index) + result.get(index + 1)) / 2;
            } else {
                return result.get(index);
            }
        } else {
            //复杂情况，两个数组合并，数据有交叉

            //先把两个集合"切"成两半，如果长度为奇数，那么把多出来那个留在右边
            Integer left1 = list1.get(list1.size() / 2);
            Integer left2 = list2.get(list2.size() / 2);

            Integer right1 = list1.get(list1.size() / 2 + 1);
            Integer right2 = list2.get(list2.size() / 2 + 1);



        }


        return null;
    }

    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        System.out.println(list.subList(0, list.size() / 2));
        System.out.println(list.subList(list.size() / 2, list.size()));
    }

    @Test
    public void test3(){
        StringBuilder builder = new StringBuilder("哈哈哈哈哈、");
        builder.replace(builder.length() - 1, builder.length(), "；");
        System.out.println(builder.toString());
    }

    @Test
    public void test4(){
        List<String> strs = Arrays.asList("123", "1231", "asdf", "asadfjkasdf", "099089");
        IntSummaryStatistics st = strs.stream().mapToInt(s -> s.length()).summaryStatistics();
        System.out.println(st);
    }

    @Test
    public void test5(){
        List<Integer> list = Arrays.asList(1, 2, 3);
        List<Integer> lst = list.stream().mapToInt(i -> i + 1).mapToObj(i -> i).collect(Collectors.toList());
        System.out.println(lst);
    }

    @Test
    public void test6(){
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();

        String s = Optional.ofNullable(map2).map(m -> m.get(1)).map(i -> map.get(i)).orElse(null);
        System.out.println(s == null);
    }




}












