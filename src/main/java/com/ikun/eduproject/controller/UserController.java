package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.UserService;
import com.ikun.eduproject.vo.ResultVO;
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
    public ResultVO login(@RequestBody User user) {
        ResultVO result = userService.regist(user);
        return result;
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public ResultVO login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {
        ResultVO result = userService.login(username, password);
        return result;
    }


}
