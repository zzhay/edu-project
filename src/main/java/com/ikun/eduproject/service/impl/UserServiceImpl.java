package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.AliOSSDeleteException;
import com.ikun.eduproject.error.EmailException;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.utils.*;
import com.ikun.eduproject.vo.*;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Resource
    private UserDao userDao;
    @Resource
    private AliOssUtils aliOSSUtils;
    @Resource
    private EmailUtil emailUtil;
    @Resource
    private RedisUtil redisUtil;

    /**
     * 注册
     *
     * @param user 用户对象
     * @return ResultVO
     */
    @Override
    @ExceptionHandler(EmailException.class)
    @Transactional(rollbackFor = MailSendException.class)
    public ResultVO<String> regist(User user) {
        //判断用户名是否已注册
        if (userDao.selectByUsername(user.getUsername()) != null) {
            return new ResultVO<>(StatusVO.REGIST_NO, "用户名已被注册", null);
        }
        //判断邮箱是否被使用
        if (userDao.selectByEmail(user.getEmail()) != null) {
            return new ResultVO<>(StatusVO.REGIST_NO, "邮箱已被使用", null);
        }
        //密码加密
        String md5Password = MD5Utils.md5(user.getPassword());
        user.setPassword(md5Password);
        int i = userDao.insertUser(user);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.REGIST_NO, "注册失败", null);
        }
        try {
            //根据身份发送邮件
            if (user.getRole() == 1) {
                emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registTeaMsg(user.getUsername()));
            } else if (user.getRole() == 2) {
                emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registStuMsg(user.getUsername()));
            }
        } catch (MailSendException e) {
            throw new EmailException("邮箱异常");
        }
        return new ResultVO<>(StatusVO.REGIST_OK, "注册成功", null);
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ResultVO
     */
    @Override
    public ResultVO<Map<String, Object>> login(String username, String password) {
        //根据用户名查询用户信息
        User user = userDao.selectByUsername(username);
        if (user == null) {
            return new ResultVO<>(StatusVO.LOGIN_NO, "用户不存在", null);
        }
        //密码加密，对比密码
        String md5Password = MD5Utils.md5(password);
        if (!Objects.equals(md5Password, user.getPassword())) {
            return new ResultVO<>(StatusVO.LOGIN_NO, "密码错误", null);
        }
        //判断账号状态是否正常，0：锁定，1：正常
        if (user.getStatu() == 0) {
            return new ResultVO<>(StatusVO.LOGIN_NO_STATU, "账号已被锁定，详情请联系管理员", null);
        } else if (user.getStatu() == 1) {
            //生成token并存入redis
            String token = TokenUtil.generateToken(username);
            redisUtil.setCacheObject(token, user, 6, TimeUnit.HOURS);
            Map<String, Object> responseData = new HashMap<>(2);
            responseData.put("token", token);
            responseData.put("user", user);
            return new ResultVO<>(StatusVO.LOGIN_OK, "登录成功", responseData);
        } else {
            return new ResultVO<>(StatusVO.LOGIN_NO_STATU, "账号审核中", null);
        }
    }

    /**
     * 更新基础信息
     *
     * @param changeInfoVO 更改信息传入对象
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor =RuntimeException.class )
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
     */
    @Override
    @Transactional(rollbackFor =RuntimeException.class )
    public ResultVO<String> updatePassword(ChangePwdVO changePwdVO) {
        //根据邮箱查询用户信息
        User user1 = userDao.selectByEmail(changePwdVO.getEmail());
        //判断新密码是否与原密码相同
        String md5Pwd1 = MD5Utils.md5(changePwdVO.getNewPwd());
        if (Objects.equals(md5Pwd1, user1.getPassword())) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "新密码和老密码相同", null);
        } else {
            //新密码加密
            changePwdVO.setNewPwd(md5Pwd1);
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
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", users);

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
     */
    @Override
    @Transactional(rollbackFor =RuntimeException.class )
    public ResultVO<String> updateStatu(String username, Integer statu) {
        int i = userDao.updateStatu(username, statu);
        if (i > 0) {
            //查询出用户信息
            User user = userDao.selectByUsername(username);
            //根据账号变更情况发送不同邮件，1：账号正常
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

    /**
     * 审核教师
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor =RuntimeException.class )
    public ResultVO<String> checkTeacher(String username, Integer statu) {
        User user = userDao.selectByUsername(username);
        if (statu == 3) {
            //statu=3，说明审核未通过，删除用户
            userDao.deleteByUsername(username);
            //发送邮件通知
            emailUtil.sendMessage(user.getEmail(), EmailMsgVO.REGIST, EmailMsgVO.registNoTeaMsg(username));
            return new ResultVO<>(StatusVO.UPDATE_OK, null, null);
        } else {
            //审核通过
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
    //开始事务,抛出 AliOSSDeleteException
    @Transactional(rollbackFor = AliOSSDeleteException.class)
    public ResultVO<String> updateImage(String username, String url) {
        User user = userDao.selectByUsername(username);
        if (user == null) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "用户不存在", null);
        }
        //获取原头像url
        String imageUrl = user.getImageUrl();
        int i = userDao.updateImage(username, url);
        if (i > 0) {
            try {
                //判断是不是默认头像
                String imageUrl1 = "https://ikun-edu.oss-cn-beijing.aliyuncs.com/teacher_default.jpg";
                String imageUrl2 = "https://ikun-edu.oss-cn-beijing.aliyuncs.com/student_default.jpg";
                if ((!imageUrl1.equals(imageUrl) && !imageUrl2.equals(imageUrl))) {
                    boolean b = aliOSSUtils.deleteImageByUrl(imageUrl);
                    //ailiOSS删除失败则抛出异常
                    if (!b) {
                        throw new AliOSSDeleteException("原图片删除错误");
                    }
                }
            } catch (AliOSSDeleteException e) {
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
     */
    @Override
    public ResultVO<String> getCaptcha(String email) {
        if (userDao.selectByEmail(email) == null) {
            return new ResultVO<>(StatusVO.EMALI_NO, "用户不存在", null);
        } else {
            //生成验证码
            String captcha = GenerateCaptchaUtil.generateCaptcha();
            //email作为key缓存验证码，设置5分钟有效期
            redisUtil.setCacheObject(email, captcha, 5, TimeUnit.MINUTES);
            //邮件发送验证码
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
    public ResultVO<User> checkCaptcha(String email, String captcha) {
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
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<User> getByUserId(Integer userId) {
        User user = userDao.selectByUserId(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", user);
    }

    /**
     * 登出
     *
     * @param token token
     * @return ResultVO
     */
    @Override
    public ResultVO<String> logOut(String token) {
        if (token == null) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "注销失败", null);
        }
        boolean b = redisUtil.deleteObject(token);
        if (b) {
            return new ResultVO<>(StatusVO.UPDATE_OK, "注销成功", null);
        } else {
            return new ResultVO<>(StatusVO.UPDATE_NO, "注销失败", null);
        }
    }


}
