package com.demo.demo.business.demo.service;

import com.demo.demo.business.demo.pojo.entity.UserEntity;

import java.util.Collection;
import java.util.Map;

/**
 * Created by liuyang on 2018/11/10
 */
public interface UserService {

    UserEntity get(Integer id);

    Map<Integer, UserEntity> getAll(Collection<Integer> ids);

    void update(UserEntity userEntity);
}
