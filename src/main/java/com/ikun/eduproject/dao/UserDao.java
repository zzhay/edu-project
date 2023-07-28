package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 * UserDao是用于访问和操作用户数据的数据访问对象
 * 该类提供了对用户数据的增删改查操作。
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
     * @param changeInfoVO
     * @return
     */
    int updateByUsername(ChangeInfoVO changeInfoVO);

    /**
     * 更新密码
     * @param changePwdVO
     * @return
     */
    int updatePassword(ChangePwdVO changePwdVO);

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

    /**
     * 按照username修改状态
     * @param username
     * @return
     */
    int updateStatu(String username);

    /**
     * 按照username更新头像
     * @param username
     * @param url
     * @return
     */
    int updateImage(String username, String url);
}
