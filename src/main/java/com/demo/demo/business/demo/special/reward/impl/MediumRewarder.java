package com.demo.demo.business.demo.special.reward.impl;

import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.business.demo.special.reward.IRewarder;

public class MediumRewarder implements IRewarder {

    @Override
    public void reward(UserEntity user) {
        String str = String.format("user 【%d】 got medium reward", user.getId());
        System.out.println(str);
    }
}
