package com.demo.demo.arithmetic.dynamicprogram;

import org.junit.Test;

/**
 * 上楼梯问题：
 * 有一个n阶台阶，一次可以走1个或者2个台阶，一共有多少种走法
 */
public class Stairs {

    @Test
    public void test() {
        System.out.println(new Stair(10).compute());
    }

}

class Stair {
    private int num;//楼梯的阶数

    private int cache1;//上上个结果
    private int cache2;//上一个结果

    Stair(int num) {
        this.num = num;
    }

    /**
     * 获取这个台阶的方法数
     */
    public int compute() {
        if (num == 1) {
            return 1;
        }
        if (num == 2) {
            return 2;
        }
        cache1 = 1;
        cache2 = 2;
        int index = 3;
        int result = cache2;
        while (index <= num) {
            result = cache1 + cache2;
            cache1 = cache2;
            cache2 = result;
            index++;
        }
        return result;
    }


}
