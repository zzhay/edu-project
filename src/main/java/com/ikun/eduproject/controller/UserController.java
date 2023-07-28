package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
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
     * @param changeInfoVO
     * @return
     */
    @ApiOperation("更新基础信息接口")
    @PostMapping("/updateInfo")
    public ResultVO updateInfo(@RequestBody ChangeInfoVO changeInfoVO) {
        ResultVO result = userService.updateInformation(changeInfoVO);
        return result;
    }

    /**
     * 更新密码
     * @param changePwdVO
     * @return
     */
    @ApiOperation("更新密码")
    @PostMapping("/updatePwd")
    public ResultVO updatePassword(@RequestBody ChangePwdVO changePwdVO) {
        ResultVO result = userService.updatePassword(changePwdVO);
        return result;
    }

    /**
     * 查出所有学生
     * @return
     */
    @ApiOperation("管理员查看所有学生")
    @GetMapping("/getStudent")
    public ResultVO getStudent() {
        ResultVO result = userService.getStudent();
        return result;
    }

    /**
     * 查出所有老师
     * @return
     */
    @ApiOperation("管理员查看所有老师")
    @GetMapping("/getTeacher")
    public ResultVO getTeacher() {
        ResultVO result = userService.getTeacher();
        return result;
    }

    @ApiOperation("管理员更新用户状态")
    @PostMapping("/updateStatu")
    public ResultVO updateStatu(@RequestParam("username") String username) {
        ResultVO result = userService.updateStatu(username);
        return result;
    }

    /**
     * 修改头像
     * @param username
     * @param url 照片地址
     * @return
     */
    @ApiOperation("修改头像")
    @PostMapping("/updateImage")
    public ResultVO updateImage(@RequestParam("username") String username, @RequestParam("url") String url){
        ResultVO result = userService.updateImage(username, url);
        return result;
    }

}
