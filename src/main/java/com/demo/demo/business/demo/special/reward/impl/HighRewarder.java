package com.demo.demo.business.demo.special.reward.impl;

import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.business.demo.special.reward.IRewarder;

public class HighRewarder implements IRewarder {

    @Override
    public void reward(UserEntity user) {
        String str = String.format("user 【%d】 got high reward", user.getId());
        System.out.println(str);
    }

}
