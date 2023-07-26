package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @param user
     * @return
     */
    @ApiOperation("注册接口")
    @PostMapping("/regist")
    public ResultVO regist(@RequestBody User user) {
        ResultVO result = userService.regist(user);
        return result;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultVO login(@RequestBody User user) {
        ResultVO result = userService.login(user.getUsername(), user.getPassword());
        return result;
    }

    /**
     * 更新基础信息
     * @param user
     * @return
     */
    @ApiOperation("更新基础信息接口")
    @PostMapping("/updateInfo")
    public ResultVO updateInfo(@RequestBody User user) {
        ResultVO result = userService.updateInformation(user);
        return result;
    }

    /**
     * 更新密码
     * @param user
     * @return
     */
    @ApiOperation("更新密码")
    @PostMapping("/updatePwd")
    public ResultVO updatePassword(@RequestBody User user) {
        ResultVO result = userService.updatePassword(user);
        return result;
    }

    /**
     * 查出所有学生
     * @return
     */
    @ApiOperation("查出所有学生")
    @GetMapping("/getStudent")
    public ResultVO getStudent() {
        ResultVO result = userService.getStudent();
        return result;
    }

    /**
     * 查出所有老师
     * @return
     */
    @ApiOperation("查出所有老师")
    @GetMapping("/getTeacher")
    public ResultVO getTeacher() {
        ResultVO result = userService.getTeacher();
        return result;
    }

}
