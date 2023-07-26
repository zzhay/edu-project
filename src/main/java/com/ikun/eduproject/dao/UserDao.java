package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.User;

import java.util.List;

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
     * 按照username更新基础信息
     * @param user
     * @return
     */
    int updateByUsername(User user);

    /**
     * 更新密码
     * @param user
     * @return
     */
    int updatePassword(User user);

    /**
     * 查出所有学生
     * @return
     */
    List<User> selectStudent();

    /**
     * 查出所有学老师
     * @return
     */
    List<User> selectTeacher();

    /**
     * 根据手机号查找
     * @param phone
     * @return
     */
    Integer selectByPhone(String phone);

    /**
     * 根据邮箱查找
     * @param email
     * @return
     */
    Integer selectByEmail(String email);
}
