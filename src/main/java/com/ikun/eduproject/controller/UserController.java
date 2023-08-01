package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
import com.ikun.eduproject.vo.ForgetPwdVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "提供用户管理的接口", tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param user 用户信息
     * @return ResultVO
     */
    @ApiOperation("注册接口")
    @PostMapping("/regist")
    public ResultVO<String> regist(@RequestBody User user) {
        return userService.regist(user);
    }

    /**
     * 登录
     *
     * @param user 用户信息
     * @return ResultVO
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultVO<User> login(@RequestBody User user) {
        return userService.login(user.getUsername(), user.getPassword());
    }

    /**
     * 更新基础信息
     *
     * @param changeInfoVO 更改信息传入对象
     * @return ResultVO
     */
    @ApiOperation("更新基础信息接口")
    @PostMapping("/updateInfo")
    public ResultVO<String> updateInfo(@RequestBody ChangeInfoVO changeInfoVO) {
        return userService.updateInformation(changeInfoVO);
    }

    /**
     * 更新密码
     *
     * @param changePwdVO 更改密码传入对象
     * @return ResultVO
     */
    @ApiOperation("更新密码")
    @PostMapping("/updatePwd")
    public ResultVO<String> updatePassword(@RequestBody ChangePwdVO changePwdVO){
        return userService.updatePassword(changePwdVO);
    }

    /**
     * 查出所有学生
     *
     * @return ResultVO
     */
    @ApiOperation("管理员查看所有学生")
    @GetMapping("/getStudent")
    public ResultVO<List<User>> getStudent() {
        return userService.getStudent();
    }

    /**
     * 查出所有老师
     *
     * @return ResultVO
     */
    @ApiOperation("管理员查看所有教师")
    @GetMapping("/getTeacher")
    public ResultVO<List<User>> getTeacher() {
        return userService.getTeacher();
    }

    /**
     * 查出待审核教师
     *
     * @return ResultVO
     */
    @ApiOperation("管理员查看待审核老教师")
    @GetMapping("/getTeacherNo")
    public ResultVO<List<User>> getTeacherNo() {
        return userService.getTeacherNo();
    }

    /**
     * 管理员更新用户状态
     * @param username 用户名
     * @param statu 状态
     * @return ResultVO
     */
    @ApiOperation("管理员更新用户状态")
    @PostMapping("/updateStatu")
    public ResultVO<String> updateStatu(@RequestParam("username") String username, @RequestParam("statu") Integer statu) {
        return userService.updateStatu(username, statu);
    }

    /**
     * 管理员审核教师
     * @param username 用户名
     * @param statu 状态
     * @return
     */
    @ApiOperation("管理员审核教师")
    @PostMapping("/checkTeacher")
    public ResultVO<String> checkTeacher(@RequestParam("username") String username, @RequestParam("statu") Integer statu){
        return userService.checkTeacher(username, statu);
    }

    /**
     * 修改头像
     *
     * @param username 用户名
     * @param url      照片地址
     * @return ResultVO
     */
    @ApiOperation("修改头像")
    @PostMapping("/updateImage")
    public ResultVO<String> updateImage(@RequestParam("username") String username, @RequestParam("url") String url) {
        return userService.updateImage(username, url);
    }

    /**
     * 发送验证码
     *
     * @return ResultVO
     */
    @ApiOperation("发送验证码")
    @GetMapping("/getCaptcha")
    public ResultVO<String> getCaptcha(@RequestParam String email) {
        return userService.getCaptcha(email);
    }

    /**
     * 验证验证码
     *
     * @return ResultVO
     */
    @ApiOperation("验证验证码")
    @GetMapping("/checkCaptcha")
    public ResultVO<User> checkCaptcha(@RequestParam String email,@RequestParam String captcha) {
        return userService.checkCaptcha(email,captcha);
    }

    /**
     * 忘记密码
     * @param forgetPwdVO 忘记密码传入参数
     * @return ResultVO
     */
    @ApiOperation("忘记密码")
    @PostMapping("/forgetPwd")
    public ResultVO<String> forgetPwd(@RequestBody ForgetPwdVO forgetPwdVO) {
        return userService.forgetPwd(forgetPwdVO);
    }
}
