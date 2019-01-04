package com.demo.demo.business.demo.special.reward;

import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.business.demo.special.reward.impl.HighRewarder;
import com.demo.demo.business.demo.special.reward.impl.LowRewarder;
import com.demo.demo.business.demo.special.reward.impl.MediumRewarder;

public enum RewarderEnum {

    HIGH(new HighRewarder()),
    MEDIUM(new MediumRewarder()),
    LOW(new LowRewarder());

    private IRewarder rewarder;

    RewarderEnum(IRewarder rewarder){
        this.rewarder = rewarder;
    }

    public void reward(UserEntity user){
        rewarder.reward(user);
    }

}
