package com.zhangtq.dao;

import com.zhangtq.pojo.User;

import java.util.List;

public interface IUserDao {
    /**
     * 查询所有用户
     * @return
     * @throws Exception
     */
    List<User> findAll() throws Exception;

    /**
     * 查询单个用户
     * @param user
     * @return
     */
    User findByCondition(User user);
}
