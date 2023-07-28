package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.MD5Utils;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVo;
import org.bouncycastle.jcajce.provider.digest.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 * UserServiceImpl是用户相关功能的Service实现类。
 * 提供了用户管理、权限验证等相关功能的具体实现。
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public ResultVO regist(User user) {
        //判断用户名是否已注册
        if (userDao.selectByUsername(user.getUsername()) == null) {
            //判断电话是否被使用
            if (userDao.selectByPhone(user.getPhone()) == null) {
                //判断邮箱是否被使用
                if (userDao.selectByEmail(user.getEmail()) == null) {
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
                    return new ResultVO(StatusVo.REGIST_NO_EMAIL, "邮箱已被使用", null);
                }
            } else {
                return new ResultVO(StatusVo.REGIST_NO_PHONE, "电话号码已被使用", null);
            }

        } else {
            return new ResultVO(StatusVo.REGIST_NO, "用户名已被注册", null);
        }
    }

    /**
     * 登录
     *
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
     * 更新基础信息
     *
     * @param changeInfoVO
     * @return
     */
    @Override
    public ResultVO updateInformation(ChangeInfoVO changeInfoVO) {
        int i = userDao.updateByUsername(changeInfoVO);
        if (i > 0) {
            return new ResultVO(StatusVo.UPDATE_OK, "更新成功", null);
        } else {
            return new ResultVO(StatusVo.UPDATE_NO, "更新失败", null);
        }
    }

    /**
     * 更新密码
     *
     * @param changePwdVO
     * @return
     */
    @Override
    public ResultVO updatePassword(ChangePwdVO changePwdVO) {
        User user1 = userDao.selectByUsername(changePwdVO.getUsername());
        //判断旧密码是否正确
        if (MD5Utils.md5(changePwdVO.getOldPwd()).equals(user1.getPassword())) {
            //判断新密码是否与原密码相同
            String md5Pwd = MD5Utils.md5(changePwdVO.getNewPwd());
            if (md5Pwd.equals(user1.getPassword())) {
                return new ResultVO(StatusVo.UPDATE_NO_NEWPWD, "密码与原密码相同", null);
            } else {
                //新密码加密
                changePwdVO.setNewPwd(md5Pwd);
                if (userDao.updatePassword(changePwdVO) > 0) {
                    return new ResultVO(StatusVo.UPDATE_OK, "更新成功", null);
                } else {
                    return new ResultVO(StatusVo.UPDATE_NO, "更新失败", null);
                }
            }
        } else {
            return new ResultVO(StatusVo.UPDATE_NO_OLDPWD, "旧密码错误", null);
        }
    }

    /**
     * 查出所有学生
     *
     * @return
     */
    @Override
    public ResultVO getStudent() {
        List<User> users = userDao.selectStudent();
        if (users.isEmpty()) {
            return new ResultVO(StatusVo.SELECT_NO, "查询失败", null);
        } else {
            return new ResultVO(StatusVo.SELECT_OK, "查询成功", users);
        }
    }

    /**
     * 查出所有老师
     *
     * @return
     */
    @Override
    public ResultVO getTeacher() {
        List<User> users = userDao.selectTeacher();
        if (users.isEmpty()) {
            return new ResultVO(StatusVo.SELECT_NO, "查询失败", null);
        } else {
            return new ResultVO(StatusVo.SELECT_OK, "查询成功", users);
        }
    }

    /**
     * 修改状态
     *
     * @param username
     * @return
     */
    @Override
    public ResultVO updateStatu(String username) {
        if (username != null) {
            int i = userDao.updateStatu(username);
            if (i > 0) {
                return new ResultVO(StatusVo.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO(StatusVo.UPDATE_NO, "更新失败", null);
            }
        } else {
            return new ResultVO(StatusVo.UPDATE_NO, "用户名错误", null);
        }
    }
}
