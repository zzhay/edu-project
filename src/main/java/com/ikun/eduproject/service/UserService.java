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
     * 注册
     *
     * @param user 用户对象
     * @return ResultVO
     */
    ResultVO<String> regist(User user);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ResultVO
     */
    ResultVO<User> login(String username, String password);

    /**
     * 更新基础信息
     *
     * @param changeInfoVO 更改信息传入对象
     * @return ResultVO
     */
    ResultVO<String> updateInformation(ChangeInfoVO changeInfoVO);

    /**
     * 修改密码
     *
     * @param changePwdVO 更改密码传入对象
     * @return ResultVO
     */
    ResultVO<String> updatePassword(ChangePwdVO changePwdVO);

    /**
     * 查出所有学生
     *
     * @return ResultVO
     */
    ResultVO<List<User>> getStudent();

    /**
     * 查出所有教师
     *
     * @return ResultVO
     */
    ResultVO<List<User>> getTeacher();

    /**
     * 查出待审核教师
     *
     * @return ResultVO
     */
    ResultVO<List<User>> getTeacherNo();

    /**
     * 修改状态
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     */
    ResultVO<String> updateStatu(String username,Integer statu);

    /**
     * 审核教师
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     */
    ResultVO<String> checkTeacher(String username,Integer statu);

    /**
     * 更新头像
     *
     * @param username 用户名
     * @param url      照片地址
     * @return ResultVO
     */
    ResultVO<String> updateImage(String username,String url);

    /**
     * 发送验证码
     * @param email 邮箱
     * @return ResultVO
     */
    ResultVO<String> getCaptcha(String email);

    /**
     * 验证验证码
     * @param email 邮箱
     * @param captcha 验证码
     * @return ResultVO
     */
    ResultVO<User> checkCaptcha(String email,String captcha);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    ResultVO<User> getByUserId(Integer userId);

}
