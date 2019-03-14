package com.demo.demo.arithmetic.dynamicprogram;

import org.junit.Test;

import java.util.*;

/**
 * 背包问题：
 * 有若干个物品和一个背包，每个物品有体积和价值，背包容量有限，如何选择几件物品放入背包已达到最高价值
 */
public class BagProblem {

    private Bag bag = new Bag(60);
    private List<Product> products = Arrays.asList(
            new Product(20, 5),
            new Product(40, 10),
            new Product(30, 8),
            new Product(66, 30),
            new Product(55, 15),
            new Product(80, 42),
            new Product(60, 20)
    );

    @Test
    public void test() {
        Collections.shuffle(products);
        int sumValue = products.stream().mapToInt(Product::getValue).sum();
        System.out.println("物品总价值：" + sumValue);
        int sumVolume = products.stream().mapToInt(Product::getVolume).sum();
        System.out.println("物品总体积：" + sumVolume);

        Choice choice = new Resolver(bag, products).getBestChoice();
        System.out.println("总价值" + choice.value);
        int totalValue = 0;
        for (int i = 0; i < choice.choices.length; i++) {
            if (choice.choices[i]) {
                totalValue += products.get(i).getValue();
            }
            System.out.print(choice.choices[i] + ",");
        }
        System.out.println("\n最终总价值：" + totalValue);
    }


}

/**
 * 最优解中，index位置的情况
 */
class Choice {
    //当前组合的总价值
    int value;
    //当前递归链的选择情况
    boolean[] choices;

    Choice(int value, int size) {
        this.value = value;
        this.choices = new boolean[size];
    }

    void setChoice(int index, boolean choice) {
        choices[index] = choice;
    }

}

class Resolver {
    private Bag bag;
    private List<Product> products;
    //存储某个index+某个capacity组合下的最优解
    private Map<String, Choice> choiceCache;

    Resolver(Bag bag, List<Product> products) {
        this.init(bag, products);
    }

    private void init(Bag bag, List<Product> products) {
        this.bag = bag;
        this.products = products;
        choiceCache = new HashMap<>();
    }

    Choice getBestChoice() {
        return this.getBestChoice(products.size() - 1, bag.getCapacity());
    }

    /**
     * 获取用capacity容量去装0~index位置的物品的最优解
     *
     * @param index    位置
     * @param capacity 当前容量
     */
    private Choice getBestChoice(int index, int capacity) {
        Product pro = products.get(index);
        int value = pro.getValue();
        int volume = pro.getVolume();

        String key = this.key(index, capacity);
        Choice cacheData = choiceCache.get(key);
        if (cacheData != null) {
//            return cacheData;
        }

        //处理边界情况
        if (index == 0) {
            Choice choice;
            //容量足够
            if (capacity >= volume) {
                choice = new Choice(value, products.size());
                choice.setChoice(0, true);
            }
            //容量不够
            else {
                choice = new Choice(0, products.size());
                choice.setChoice(0, false);
            }
            choiceCache.put(key, choice);
            return choice;
        }
        //如果给定的容量太小
        if (capacity < volume) {
            Choice choice = this.getBestChoice(index - 1, capacity);
            choice.setChoice(index, false);
            choiceCache.put(key, choice);
            return choice;
        }

        //给定容量足够放置当前物品，因此需要递归，比较当前物品装与不装的优劣
        //当前物品装进去
        Choice loadChoice = this.getBestChoice(index - 1, capacity - volume);
        //当前物品不装进去
        Choice unloadChoice = this.getBestChoice(index - 1, capacity);
        Choice choice;
        if (loadChoice.value + value >= unloadChoice.value) {
            choice = loadChoice;
            choice.value += value;
            choice.setChoice(index, true);
        } else {
            choice = unloadChoice;
            choice.setChoice(index, false);
        }

        choiceCache.put(key, choice);
        return choice;
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

    int getCapacity() {
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

    int getValue() {
        return value;
    }

    int getVolume() {
        return volume;
    }
}