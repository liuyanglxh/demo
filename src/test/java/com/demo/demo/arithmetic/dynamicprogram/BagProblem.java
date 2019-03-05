package com.demo.demo.arithmetic.dynamicprogram;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 背包问题：
 * 有若干个物品和一个背包，每个物品有体积和价值，背包容量有限，如何选择几件物品放入背包已达到最高价值
 */
public class BagProblem {

    private Bag bag = new Bag(100);
    private List<Product> products = Arrays.asList(
            new Product(20, 5),
            new Product(40, 10),
            new Product(30, 8),
            new Product(66, 30),
            new Product(55, 15),
            new Product(80, 42),
            new Product(60, 20)
    );
    private boolean[] choice = new boolean[products.size()];

    @Test
    public void test() {
        int sumValue = products.stream().mapToInt(Product::getValue).sum();
        System.out.println("总价值：" + sumValue);
        int sumVolume = products.stream().mapToInt(Product::getVolume).sum();
        System.out.println("总体积：" + sumVolume);

        Result result = new Resolver(bag, products).getChoice();
        System.out.println(result.getValue());
        for (boolean b : result.getChoice()) {
            System.out.println(b);
        }
    }


}

class Result {
    private int value;
    private boolean[] choice;

    Result(int value, boolean[] choice) {
        this.value = value;
        this.choice = choice;
    }

    int getValue() {
        return value;
    }

    boolean[] getChoice() {
        return choice;
    }
}

class Resolver {
    private Bag bag;
    private List<Product> products;
    private boolean[] choice;
    private Map<String, Integer> cache;

    Resolver(Bag bag, List<Product> products) {
        this.init(bag, products);
    }

    private void init(Bag bag, List<Product> products) {
        this.bag = bag;
        this.products = products;
        choice = new boolean[products.size()];
        cache = new HashMap<>();
    }

    Result getChoice() {
        int value = this.choice(products.size() - 1, bag.getCapacity());
        return new Result(value, choice);
    }

    private int choice(int index, int capacity) {
        Product pro = products.get(index);
        int value = pro.getValue();
        int volume = pro.getVolume();

        String key = this.key(index, capacity);
        Integer cacheResult = cache.get(key);
        if (cacheResult != null) {
            return cacheResult;
        }

        //处理边界情况
        //给定的容量不够装这件物品
        if (capacity < volume) {
            choice[index] = false;
            cache.put(key, 0);
            return 0;
        }
        //如果index是第一件物品，且容量足够
        if (index == 0) {
            choice[index] = true;
            cache.put(key, value);
            return value;
        }

        //递归
        //当前物品装进去
        int load = this.choice(index - 1, capacity - volume) + value;
        //当前物品不装进去
        int unload = this.choice(index - 1, capacity);
        int max = max(load, unload, index);
        cache.put(key, max);
        return max;
    }

    private int max(int load, int unload, int index) {
        if (load >= unload) {
            choice[index] = true;
            return load;
        }
        choice[index] = false;
        return unload;
    }

    private String key(int index, int capacity) {
        return index + "_" + capacity;
    }
}

class Bag {
    private int capacity;//背包容量

    Bag(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Product {
    private int value;//价值
    private int volume;//体积

    Product(int value, int volume) {
        this.value = value;
        this.volume = volume;
    }

    public int getValue() {
        return value;
    }

    public int getVolume() {
        return volume;
    }
}