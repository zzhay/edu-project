package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.StudentCourse;
import com.ikun.eduproject.service.StuCoursesService;
import com.ikun.eduproject.vo.LoginVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/stuCourses")
@CrossOrigin
@Api(value = "提供学生的课程管理的接口", tags = "学生的课程管理")
public class StuCoursesController {

    @Autowired
    private StuCoursesService stuCoursesService;

    @ApiOperation("购买课程")
    @PostMapping("/buyCourse")
    public ResultVO<String> buyCourse(@RequestBody @Valid StudentCourse studentCourse) {
        return stuCoursesService.buyCourse(studentCourse);
    }

    @ApiOperation("验证密码")
    @PostMapping("/checkPwd")
    public ResultVO<String> checkPwd(@RequestBody @Valid LoginVO loginVO) {
        return stuCoursesService.checkPwd(loginVO);
    }

    @ApiOperation("显示所购买的课程")
    @PostMapping("/getOwnCourse")
    public ResultVO<List<Course>> getOwnCourse(@RequestParam @NotNull(message = "用户id不能为空") Integer userId) {
        return stuCoursesService.getOwnCourse(userId);
    }

}
