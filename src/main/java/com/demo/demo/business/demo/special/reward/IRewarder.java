package com.demo.demo.business.demo.special.reward;

import com.demo.demo.business.demo.pojo.entity.UserEntity;

/**
 * 试验代码
 */
@FunctionalInterface
public interface IRewarder {

    void reward(UserEntity user);
}
