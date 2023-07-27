package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
import com.ikun.eduproject.vo.ResultVO;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 * UserService提供了用户相关功能的接口。
 * 定义了用户管理、权限验证等相关功能的抽象方法。
 */
public interface UserService {
    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    ResultVO login(String username, String password);

    /**
     * 注册
     *
     * @param user
     * @return
     */
    ResultVO regist(User user);

    /**
     * 更新基础信息
     *
     * @param changeInfoVO
     * @return
     */
    ResultVO updateInformation(ChangeInfoVO changeInfoVO);

    /**
     * 更新密码
     *
     * @param changePwdVO
     * @return
     */
    ResultVO updatePassword(ChangePwdVO changePwdVO);

    /**
     * 查出所有学生
     *
     * @return
     */
    ResultVO getStudent();

    /**
     * 查出所有老师
     *
     * @return
     */
    ResultVO getTeacher();

    /**
     * 修改状态
     *
     * @param username
     * @return
     */
    ResultVO updateStatu(String username);

}
