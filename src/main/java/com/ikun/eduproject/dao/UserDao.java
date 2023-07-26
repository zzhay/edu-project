package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.User;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
public interface UserDao {
    /**
     * 按照用户名查询
     * @param username
     * @return User对象
     */
    User selectByUsername(String username);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 按照username更新
     * @param user
     * @return
     */
    int updateByUsername(User user);
}
