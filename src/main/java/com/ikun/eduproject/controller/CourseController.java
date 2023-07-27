package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
@Api(value = "提供课程管理的接口", tags = "课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("新增课程")
    @PostMapping("/addCourse")
    public ResultVO addCourse(Course course) {
        ResultVO result = courseService.addCourse(course);
        return result;
    }

    @ApiOperation("教师查看名下课程")
    @PostMapping("/getOwnCourse")
    public ResultVO getOwnCourse(int userId) {
        ResultVO result = courseService.getOwnCourse(userId);
        return result;
    }
}
