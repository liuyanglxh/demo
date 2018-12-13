package com.demo.demo.business.demo.dao.cache;

import com.demo.demo.business.demo.pojo.entity.UserEntity;

/**
 * Created by liuyang on 2018/11/10
 */
public interface UserCache {

    UserEntity get(Integer id);

    void set(UserEntity userEntity);

    void remove(Integer id);

}
