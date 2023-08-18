package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "提供用户管理的接口", tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 注册
     *
     * @param user 用户信息
     * @return ResultVO
     */
    @ApiOperation("注册接口")
    @PostMapping("/regist")
    public ResultVO<String> regist(@RequestBody @Valid User user) {
        //todo 注册时绑定邮箱
        return userService.regist(user);
    }

    /**
     * 登录
     *
     * @param loginVO 登录信息
     * @return ResultVO
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultVO<Map<String, Object>> login(@RequestBody @Valid LoginVO loginVO) {
        return userService.login(loginVO.getUsername(), loginVO.getPassword());
    }

    /**
     * 更新基础信息
     *
     * @param changeInfoVO 更改信息传入对象
     * @return ResultVO
     */
    @ApiOperation("更新基础信息接口")
    @PostMapping("/updateInfo")
    public ResultVO<User> updateInfo(@RequestBody @Valid ChangeInfoVO changeInfoVO) {
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
    public ResultVO<String> updatePassword(@RequestBody @Valid ChangePwdVO changePwdVO) {
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
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     */
    @ApiOperation("管理员更新用户状态")
    @PostMapping("/updateStatu")
    public ResultVO<String> updateStatu(@RequestParam("username") @NotNull(message = "用户名不能为空") String username,
                                        @RequestParam("statu") @NotNull(message = "状态不能为空") Integer statu) {
        return userService.updateStatu(username, statu);
    }

    /**
     * 管理员审核教师
     *
     * @param username 用户名
     * @param statu    状态
     * @return ResultVO
     */
    @ApiOperation("管理员审核教师")
    @PostMapping("/checkTeacher")
    public ResultVO<String> checkTeacher(@RequestParam("username") @NotNull(message = "用户名不能为空") String username,
                                         @RequestParam("statu") @NotNull(message = "状态不能为空") Integer statu) {
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
    public ResultVO<String> updateImage(@RequestParam("username") @NotNull(message = "用户名不能为空") String username,
                                        @RequestParam("url") @NotNull(message = "url不能为空") String url) {
        return userService.updateImage(username, url);
    }

    /**
     * 发送验证码
     *
     * @return ResultVO
     */
    @ApiOperation("发送验证码")
    @GetMapping("/getCaptcha")
    public ResultVO<String> getCaptcha(@RequestParam @NotNull(message = "邮箱不能为空")String email) {
        return userService.getCaptcha(email);
    }

    /**
     * 验证验证码
     *
     * @return ResultVO
     */
    @ApiOperation("验证验证码")
    @GetMapping("/checkCaptcha")
    public ResultVO<User> checkCaptcha(@RequestParam @NotNull(message = "邮箱不能为空")String email,
                                       @RequestParam @NotNull(message = "验证码不能为空")String captcha) {
        return userService.checkCaptcha(email, captcha);
    }

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("获取用户信息")
    @PostMapping("/getUser")
    public ResultVO<User> getUser(@RequestParam("userId") @NotNull(message = "用户id不能为空") Integer userId) {
        return userService.getByUserId(userId);
    }

    /**
     * 登出
     * @param request http请求
     * @return ResultVO
     */
    @ApiOperation("登出")
    @PostMapping("/logOut")
    public ResultVO<String> logOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        return userService.logOut(token);
    }

}
