package com.demo.demo.business.demo.special.reward;

import com.demo.demo.business.demo.pojo.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class RewardExecutor {

    private List<RewarderEnum> rewarders = new ArrayList<>();
    private UserEntity user;

    public RewardExecutor(UserEntity user) {
        this.user = user;
    }

    public RewardExecutor add(RewarderEnum rewarder) {
        rewarders.add(rewarder);
        return this;
    }

    public boolean execute() {
        try {
            rewarders.forEach(r -> r.reward(user));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
