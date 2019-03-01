package com.demo.demo.business.demo.pojo.entity;

/**
 * Created by liuyang on 2018/11/10
 */
public class UserEntity {

    private Integer id;
    private String username;
    private byte sex;           // 0--女  1--男  2--未知
    private Integer score;

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
