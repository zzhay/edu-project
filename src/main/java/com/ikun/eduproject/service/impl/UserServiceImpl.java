package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.ImageDeletionException;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.utils.*;
import com.ikun.eduproject.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 注册
     *
     * @param user 用户对象
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> regist(User user) throws MailSendException {
        //判断用户名是否已注册
        if (userDao.selectByUsername(user.getUsername()) != null) {
            return new ResultVO<>(StatusVO.REGIST_NO, "用户名已被注册", null);
        }
        //判断电话是否被使用
        if (userDao.selectByPhone(user.getPhone()) != null) {
            return new ResultVO<>(StatusVO.REGIST_NO_PHONE, "电话号码已被使用", null);
        }
        //判断邮箱是否被使用
        if (userDao.selectByEmail(user.getEmail()) != null) {
            return new ResultVO<>(StatusVO.REGIST_NO_EMAIL, "邮箱已被使用", null);
        }
        //密码加密
        String md5Password = MD5Utils.md5(user.getPassword());
        user.setPassword(md5Password);
        int i = userDao.insertUser(user);
        if (i > 0) {
            //根据身份发送邮件
            if (user.getRole() == 1) {
                emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registTeaMsg(user.getUsername()));
            } else if (user.getRole() == 2) {
                emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registStuMsg(user.getUsername()));
            }
            return new ResultVO<>(StatusVO.REGIST_OK, "注册成功", null);
        } else {
            return new ResultVO<>(StatusVO.REGIST_NO, "注册失败", null);
        }
    }


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ResultVO
     */
    @Override
    public ResultVO<User> login(String username, String password) {
        //判断为空
        if (username == null || password == null) {
            return new ResultVO<>(StatusVO.LOGIN_NO, "用户名或密码为空", null);
        }
        User user = userDao.selectByUsername(username);
        if (user == null) {
            return new ResultVO<>(StatusVO.LOGIN_NO, "用户不存在", null);
        }
        //密码加密，对比密码
        String md5Password = MD5Utils.md5(password);
        if (Objects.equals(md5Password, user.getPassword())) {
            //判断账号状态是否正常
            if (user.getStatu() == 0) {
                return new ResultVO<>(StatusVO.LOGIN_NO_STATU, "账号已被锁定", null);
            } else if (user.getStatu() == 1) {
                return new ResultVO<>(StatusVO.LOGIN_OK, "登录成功", user);
            } else {
                return new ResultVO<>(StatusVO.LOGIN_NO_STATU, "账号审核中", null);
            }
        } else {
            return new ResultVO<>(StatusVO.LOGIN_NO, "密码错误", null);
        }
    }

    /**
     * 更新基础信息
     *
     * @param changeInfoVO 更改信息传入对象
     * @return ResultVO
     */
    @Override
    public ResultVO<String> updateInformation(ChangeInfoVO changeInfoVO) {
        int i = userDao.updateByUsername(changeInfoVO);
        if (i > 0) {
            return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功", null);
        } else {
            return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
        }
    }

    /**
     * 更新密码
     *
     * @param changePwdVO 更改密码传入对象
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> updatePassword(ChangePwdVO changePwdVO) throws MailSendException {
        User user1 = userDao.selectByUsername(changePwdVO.getUsername());
        //判断旧密码是否正确
        if (Objects.equals(changePwdVO.getOldPwd(), user1.getPassword())) {
            return new ResultVO<>(StatusVO.UPDATE_NO_OLDPWD, "旧密码错误", null);
        }
        //判断新密码是否与原密码相同
        String md5Pwd = MD5Utils.md5(changePwdVO.getNewPwd());
        if (Objects.equals(md5Pwd, user1.getPassword())) {
            return new ResultVO<>(StatusVO.UPDATE_NO_NEWPWD, "密码与原密码相同", null);
        } else {
            //新密码加密
            changePwdVO.setNewPwd(md5Pwd);
            if (userDao.updatePassword(changePwdVO) > 0) {
                emailUtil.sendMessage(user1.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg2(user1.getUsername()));
                return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
            }
        }

    }

    /**
     * 查出所有学生
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<User>> getStudent() {
        List<User> users = userDao.selectStudent();
        if (users.isEmpty()) {
            return new ResultVO<>(StatusVO.SELECT_NO, "查询失败", null);
        } else {
            return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", users);
        }
    }

    /**
     * 查出所有教师
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<User>> getTeacher() {
        List<User> users = userDao.selectTeacher();
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", users);
    }

    /**
     * 查出待审核教师
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<User>> getTeacherNo() {
        List<User> users = userDao.selectTeacherNo();
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", users);
    }

    /**
     * 修改状态
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> updateStatu(String username, Integer statu) throws MailSendException {

        if (username == null || statu == null) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "参数错误", null);
        } else {
            User user = userDao.selectByUsername(username);
            int i = userDao.updateStatu(username, statu);
            if (i > 0) {
                //账号解冻
                if (statu == 1) {
                    emailUtil.sendMessage(user.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg1(username));
                } else {
                    emailUtil.sendMessage(user.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg0(username));
                }
                return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
            }
        }
    }

    /**
     * 审核教师
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> checkTeacher(String username, Integer statu) throws MailSendException {
        User user = userDao.selectByUsername(username);
        //statu=3，说明审核未通过，删除用户
        if (statu == 3) {
            userDao.deleteByUsername(username);
            emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registNoTeaMsg(username));
            return new ResultVO<>(StatusVO.UPDATE_OK, null, null);
        } else {
            int i = userDao.updateStatu(username, statu);
            if (i > 0) {
                //更新成功，发送邮件
                emailUtil.sendMessage(user.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.registOKTeaMsg(username));
                return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
            }
        }
    }

    /**
     * 更新头像
     *
     * @param username 用户名
     * @param url      照片地址
     * @return ResultVO
     */
    @Override
    //开始事务,抛出 ImageDeletionException异常则事务回滚
    @Transactional(rollbackFor = ImageDeletionException.class)
    public ResultVO<String> updateImage(String username, String url) {
        User user = userDao.selectByUsername(username);
        if (user == null) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "用户不存在", null);
        }
        String imageUrl = user.getImageUrl();
        int i = userDao.updateImage(username, url);
        if (i > 0) {
            try {
                //判断是不是默认头像
                if ((!"https://ikun-edu.oss-cn-beijing.aliyuncs.com/teacher_default.jpg".equals(imageUrl) &&
                        !"https://ikun-edu.oss-cn-beijing.aliyuncs.com/student_default.jpg".equals(imageUrl))) {
                    boolean b = aliOSSUtils.deleteImageByUrl(imageUrl);
                    //ailiOSS删除失败则抛出异常
                    if (!b) {
                        throw new ImageDeletionException("原图片删除错误");
                    }
                }
            } catch (ImageDeletionException e) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "修改失败", null);
            }
            return new ResultVO<>(StatusVO.UPDATE_OK, "修改成功", null);
        } else {
            return new ResultVO<>(StatusVO.UPDATE_NO, "修改失败", null);
        }
    }

    /**
     * 发送验证码
     *
     * @param email 邮箱
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> getCaptcha(String email) throws MailSendException {
        if (email == null) {
            return new ResultVO<>(StatusVO.EMALI_NO, "邮箱不能为空", null);
        }
        if (userDao.selectByEmail(email) == null) {
            return new ResultVO<>(StatusVO.EMALI_NO, "用户不存在", null);
        } else {
            String captcha = GenerateCaptchaUtil.generateCaptcha();
            redisUtil.setCacheObject(email, captcha, 5, TimeUnit.MINUTES);
            emailUtil.sendMessage(email, EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg3(captcha));
            return new ResultVO<>(StatusVO.EMALI_OK, "邮件已发送", null);
        }
    }

    /**
     * 验证验证码
     *
     * @param email   邮箱
     * @param captcha 验证码
     * @return ResultVO
     */
    @Override
    public ResultVO<User> checkCaptcha(String email, String captcha){
        if (captcha == null) {
            return new ResultVO<>(StatusVO.CAPTCHA_NO, "验证码不能为空", null);
        }
        //取出缓存的验证码
        String cacheObject = redisUtil.getCacheObject(email);
        //判断验证码是否相同
        if (Objects.equals(cacheObject, captcha)) {
            User user = userDao.selectByEmail(email);
            return new ResultVO<>(StatusVO.CAPTCHA_OK, "验证成功", user);
        } else {
            return new ResultVO<>(StatusVO.CAPTCHA_NO, "验证码错误", null);
        }
    }

    /**
     * @param forgetPwdVO 忘记密码传入参数
     * @return ResultVO
     * @throws MailSendException 邮件发送异常情况
     */
    @Override
    public ResultVO<String> forgetPwd(ForgetPwdVO forgetPwdVO) throws MailSendException {
        //加密密码
        String md5Pwd = MD5Utils.md5(forgetPwdVO.getPassword());
        //new一个更新密码 ChangePwdVO对象
        ChangePwdVO changePwdVO = new ChangePwdVO();
        changePwdVO.setNewPwd(md5Pwd);
        changePwdVO.setUsername(forgetPwdVO.getUsername());
        //更新密码并判断是否更新成功
        if (userDao.updatePassword(changePwdVO) > 0) {
            emailUtil.sendMessage(forgetPwdVO.getEmail(), EmailMsgVO.ACCOUNT, EmailMsgVO.accountMsg2(forgetPwdVO.getUsername()));
            return new ResultVO<>(StatusVO.UPDATE_OK, "修改成功", null);
        } else {
            return new ResultVO<>(StatusVO.UPDATE_NO, "修改失败", null);
        }

    }
}
