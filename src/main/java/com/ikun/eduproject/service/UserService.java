package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ResultVO;

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


}
