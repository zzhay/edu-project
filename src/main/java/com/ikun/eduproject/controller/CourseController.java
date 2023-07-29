package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultVO addCourse(@RequestBody Course course) {
        ResultVO result = courseService.addCourse(course);
        return result;
    }

    @ApiOperation("教师查看已上架课程")
    @GetMapping("/getCourse1")
    public ResultVO getCourse1(@RequestParam("userId") int userId) {
        ResultVO result = courseService.getCourse1(userId);
        return result;
    }

    @ApiOperation("教师查看待审核课程")
    @GetMapping("/getCourse2")
    public ResultVO getCourse2(@RequestParam("userId") int userId) {
        ResultVO result = courseService.getCourse2(userId);
        return result;
    }

    @ApiOperation("教师查看审核未通过课程")
    @GetMapping("/getCourse3")
    public ResultVO getCourse3(@RequestParam("userId") int userId) {
        ResultVO result = courseService.getCourse3(userId);
        return result;
    }

    @ApiOperation("教师查看已下架课程")
    @GetMapping("/getCourse4")
    public ResultVO getCourse4(@RequestParam("userId") int userId) {
        ResultVO result = courseService.getCourse4(userId);
        return result;
    }

    @ApiOperation("教师修改课程信息")
    @PostMapping("/updateCourse")
    public ResultVO updateCourse(@RequestBody Course course) {
        ResultVO result = courseService.updateCourse(course);
        return result;
    }

    @ApiOperation("教师下架课程")
    @PostMapping("/updateStatu")
    public ResultVO updateStatu(@RequestParam("courseId") String courseId) {
        ResultVO result = courseService.updateStatu(courseId);
        return result;
    }

    @ApiOperation("教师删除未通过课程")
    @PostMapping("/deleteReq")
    public ResultVO deleteReq(@RequestParam("courseId") String courseId) {
        ResultVO result = courseService.deleteReq(courseId);
        return result;
    }

    @ApiOperation("管理员查看所有待审核课程")
    @GetMapping("/getChecked")
    public ResultVO getChecked() {
        ResultVO result = courseService.getChecked();
        return result;
    }

    @ApiOperation("管理员审核课程")
    @PostMapping("/updateChecked")
    public ResultVO updateChecked(@RequestParam("courseId") Integer courseId, @Param("checked") Integer checked) {
        ResultVO result = courseService.updateChecked(courseId, checked);
        return result;
    }
    @ApiOperation("按照课程类别查看课程")
    @GetMapping("/getByCategory")
    public ResultVO getByCategory(@RequestParam("category") String category) {
        ResultVO result = courseService.getByCategory(category);
        return result;
    }

}
