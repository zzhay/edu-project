package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.ImageDeletionException;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.MD5Utils;
import com.ikun.eduproject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private AliOSSUtils aliOSSUtils;
    @Autowired
    private EmailUtil emailUtil;

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
                        //根据身份发送邮件
                        if (user.getRole()==1){
                            emailUtil.sendMessage(user.getEmail(),EmailMsgVO.REGIST,EmailMsgVO.registTeaMsg(user.getUsername()));
                        } else if (user.getRole() == 2) {
                            emailUtil.sendMessage(user.getEmail(),EmailMsgVO.REGIST,EmailMsgVO.registStuMsg(user.getUsername()));
                        }
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
                    //判断账号状态是否正常
                    if (user.getStatu() == 0) {
                        return new ResultVO(StatusVo.LOGIN_NO_STATU, "账号被锁定", null);
                    } else if (user.getStatu() == 1) {
                        return new ResultVO(StatusVo.LOGIN_OK, "登录成功", user);
                    } else {
                        return new ResultVO(StatusVo.LOGIN_NO_STATU, "账号审核中", null);
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
                    emailUtil.sendMessage(user1.getEmail(),EmailMsgVO.ACCOUNT,EmailMsgVO.accountMsg2(user1.getUsername()));
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
     * 查出正常教师
     *
     * @return
     */
    @Override
    public ResultVO getTeacher() {
        List<User> users = userDao.selectTeacher();
        return new ResultVO(StatusVo.SELECT_OK, "查询成功", users);
    }

    /**
     * 查出待审核教师
     *
     * @return
     */
    @Override
    public ResultVO getTeacherNo() {
        List<User> users = userDao.selectTeacherNo();
        return new ResultVO(StatusVo.SELECT_OK, "查询成功", users);
    }


    /**
     * 更新头像
     *
     * @param username
     * @param url      照片地址
     * @return
     */
    @Override
    //开始事务,抛出 ImageDeletionException异常则事务回滚
    @Transactional(rollbackFor = ImageDeletionException.class)
    public ResultVO updateImage(String username, String url) {
        User user = userDao.selectByUsername(username);
        if (user != null) {
            String imageUrl = user.getImageUrl();
            int i = userDao.updateImage(username, url);
            if (i > 0) {
                try {
                    boolean b = aliOSSUtils.deleteImageByUrl(imageUrl);
                    //ailiOSS删除失败则抛出异常
                    if (!b) {
                        throw new ImageDeletionException("原图片删除错误");
                    }
                } catch (ImageDeletionException e) {
                    return new ResultVO(StatusVo.UPDATE_NO, "修改失败", null);
                }
                return new ResultVO(StatusVo.UPDATE_OK, "修改成功", null);
            } else {
                return new ResultVO(StatusVo.UPDATE_NO, "修改失败", null);
            }
        } else {
            return new ResultVO(StatusVo.UPDATE_NO, "用户不存在", null);
        }
    }

    /**
     * 修改状态
     *
     * @param username
     * @return
     */
    @Override
    public ResultVO updateStatu(String username, Integer statu) {
        if (username != null) {
            User user = userDao.selectByUsername(username);
            //statu=3，说明审核未通过，删除用户
            if (statu==3) {
                userDao.deleteByUsername(username);
                emailUtil.sendMessage(user.getEmail(),EmailMsgVO.REGIST,EmailMsgVO.registNoTeaMsg(username));
                return new ResultVO(StatusVo.UPDATE_OK, null, null);
            } else {
                //其它情况是正常用户的状态变动
                int i = userDao.updateStatu(username, statu);
                if (i > 0) {
                    //账号解冻
                    if (statu == 1) {
                        emailUtil.sendMessage(user.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg1(username));
                    } else {
                        emailUtil.sendMessage(user.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg0(username));
                    }
                    return new ResultVO(StatusVo.UPDATE_OK, "更新成功", null);
                } else {
                    return new ResultVO(StatusVo.UPDATE_NO, "更新失败", null);
                }
            }
        } else {
            return new ResultVO(StatusVo.UPDATE_NO, "用户名错误", null);
        }
    }
}
