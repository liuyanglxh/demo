package com.demo.demo.common.pojo.MutableNumber;

import java.util.Objects;

/**
 * 可变的int类型
 */
public class MutableInteger extends Number implements Comparable<Integer>{

    private int value;

    public MutableInteger(){}

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    public MutableInteger(int value) {
        this.value = value;
    }

    public int addAndGet(int i) {
        this.value += i;
        return value;
    }

    public int getAndAdd(int i) {
        int j = this.value;
        this.value += i;
        return j;
    }

    public int divide(int i) {
        this.value /= i;
        return value;
    }

    public int multiply(int i) {
        this.value *= i;
        return value;
    }

    public void set(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutableInteger that = (MutableInteger) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(Integer o) {
        return value - o;
    }
}