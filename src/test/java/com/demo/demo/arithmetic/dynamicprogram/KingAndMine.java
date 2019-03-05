package com.demo.demo.arithmetic.dynamicprogram;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 国王金库问题
 */
public class KingAndMine {

    @Test
    public void test1() {

        //添加金矿
        List<Mine> mines = Arrays.asList(
                new Mine(100, 5),
                new Mine(120, 7),
                new Mine(80, 3),
                new Mine(90, 3),
                new Mine(95, 4),
                new Mine(200, 12),
                new Mine(30, 1),
                new Mine(180, 10),
                new Mine(300, 12),
                new Mine(120, 6),
                new Mine(160, 8)
        );

        int workers = 40;

        System.out.println(Mine.compute(mines, workers));

    }


}

/**
 * 金矿问题
 */
class Mine {
    private int gold;//产出的金币
    private int workers;//所需要的工人数

    Mine(int gold, int workers) {
        this.gold = gold;
        this.workers = workers;
    }

    static int compute(List<Mine> mines, int workers) {
        Computer computer = new Computer(mines, workers);
        int prod = computer.getProd();
        System.out.println(computer.count);
        return prod;
    }

    static class Computer {
        List<Mine> mines;
        int workers;

        private Map<String, Integer> cache = new HashMap<>();
        private int count = 0;

        Computer(List<Mine> mines, int workers) {
            this.mines = mines;
            this.workers = workers;
        }

        int getProd() {
            return this.getProd(mines.size() - 1, workers, mines);
        }

        /**
         * 计算第index个金矿的产出
         *
         * @param index   金矿编号
         * @param workers 前index个金矿所需要的工人数
         */
        private int getProd(int index, int workers, List<Mine> mines) {

            Integer cacheResult = cache.get(this.getKey(index, workers));
            if (cacheResult != null) {
                return cacheResult;
            }
            int result;
            count++;

            Mine mine = mines.get(index);
            int workersNeeded = mine.workers;

            //第一座金矿：工人数够就返回产出，不够就返回0
            if (index == 0) {
                result = workers >= workersNeeded ? mine.gold : 0;
                cache.put(this.getKey(index, workers), result);
                return result;
            }

            //不是第一座金矿
            //如果当前剩的工人不够挖当前的，那就不挖
            if (workers < workersNeeded) {
                result = this.getProd(index - 1, workers, mines);
                cache.put(this.getKey(index, workers), result);
                return result;
            }

            result = this.max(this.getProd(index - 1, workers, mines),
                    this.getProd(index - 1, workers - mine.workers, mines) + mine.gold);
            cache.put(this.getKey(index, workers), result);
            return result;
        }

        private int max(int a, int b) {
            return a > b ? a : b;
        }

        private String getKey(int index, int workers) {
            return index + "_" + workers;
        }
    }
}

