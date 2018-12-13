package com.demo.demo.business.demo.service.impl;

import com.demo.demo.business.demo.dao.cache.impl.UserCacheImpl;
import com.demo.demo.business.demo.event.UserEventModel;
import com.demo.demo.business.demo.pojo.entity.UserEntity;
import com.demo.demo.business.demo.service.UserService;
import com.demo.demo.common.advice.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * Created by liuyang on 2018/11/10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEventModel userEventModel;

    @Override
    public UserEntity get(Integer id) {
        return null;
    }

    @Override
    public Map<Integer, UserEntity> getAll(Collection<Integer> ids) {
        return null;
    }

    @Override
    public void update(UserEntity userEntity) {
        if (userEntity != null && userEntity.getId() != null){
            //用户信息有更新时，将用户信息发送到kafka
            userEventModel.sendAsync(userEntity);
        } else {
            throw new BusinessException("id不能为空!");
        }
    }
}
