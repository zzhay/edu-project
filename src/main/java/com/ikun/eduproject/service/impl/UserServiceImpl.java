package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.utils.MD5Utils;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ResultVO login(String username, String password) {
        //判断为空
        if (username.isEmpty() || password.isEmpty()) {
            return new ResultVO(StatusVo.LOGIN_NO, "用户名或密码为空", null);
        } else {
            User user = userDao.selectByUsername(username);
            if (user == null) {
                return new ResultVO(StatusVo.LOGIN_NO, "用户不存在", null);
            } else {
                //密码加密，对比密码
                String md5Password = MD5Utils.md5(password);
                if (md5Password.equals(user.getPassword())) {
                    //判断账号是否被锁定
                    if (user.getStatu() == 1) {
                        return new ResultVO(StatusVo.LOGIN_OK, "登录成功", user);
                    } else {
                        return new ResultVO(StatusVo.LOGIN_NO_STATU, "账号被锁定", null);
                    }
                } else {
                    return new ResultVO(StatusVo.LOGIN_NO, "密码错误", null);
                }


            }
        }
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public ResultVO regist(User user) {
        //判断用户名是否已注册
        if (userDao.selectByUsername(user.getUsername()) == null) {
            //密码加密
            String md5Password = MD5Utils.md5(user.getPassword());
            user.setPassword(md5Password);
            int i = userDao.insertUser(user);
            if (i > 0) {
                return new ResultVO(StatusVo.REGIST_OK, "注册成功", null);
            } else {
                return new ResultVO(StatusVo.REGIST_NO, "注册失败", null);
            }
        } else {
            return new ResultVO(StatusVo.REGIST_NO, "用户名已被注册", null);
        }
    }
}
