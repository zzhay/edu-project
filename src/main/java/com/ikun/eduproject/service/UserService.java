package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ResultVO;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ResultVO login(String username, String password);

    /**
     * 注册
     * @param user
     * @return
     */
    ResultVO regist(User user);

    /**
     * 更新基础信息
     * @param user
     * @return
     */
    ResultVO updateInformation(User user);

    /**
     * 更新密码
     * @param user
     * @return
     */
    ResultVO updatePassword(User user);

    /**
     * 查出所有学生
     * @return
     */
    ResultVO getStudent();

    /**
     * 查出所有老师
     * @return
     */
    ResultVO getTeacher();

}
