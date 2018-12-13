package com.demo.demo.java8;

/**
 * Created by liuyang on 2018/11/24
 */
public class TestBean {

    private String name;

    private Integer age;

    public static TestBean of(String name, Integer age){
        TestBean bean = new TestBean();
        bean.name = name;
        bean.age = age;
        return bean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
