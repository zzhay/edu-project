package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    public ResultVO addCourse(@RequestBody @Valid Course course) {
        ResultVO result = courseService.addCourse(course);
        return result;
    }

    @ApiOperation("教师查看已上架课程")
    @GetMapping("/getCourse1")
    public ResultVO getCourse1(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        ResultVO result = courseService.getCourse1(userId);
        return result;
    }

    @ApiOperation("教师查看待审核课程")
    @GetMapping("/getCourse2")
    public ResultVO getCourse2(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        ResultVO result = courseService.getCourse2(userId);
        return result;
    }

    @ApiOperation("教师查看审核未通过课程")
    @GetMapping("/getCourse3")
    public ResultVO getCourse3(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        ResultVO result = courseService.getCourse3(userId);
        return result;
    }

    @ApiOperation("教师查看已下架课程")
    @GetMapping("/getCourse4")
    public ResultVO getCourse4(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        ResultVO result = courseService.getCourse4(userId);
        return result;
    }

    @ApiOperation("教师修改课程信息")
    @PostMapping("/updateCourse")
    public ResultVO updateCourse(@RequestBody @Valid Course course) {
        ResultVO result = courseService.updateCourse(course);
        return result;
    }

    @ApiOperation("教师下架课程")
    @PostMapping("/updateStatu")
    public ResultVO updateStatu(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
        ResultVO result = courseService.updateStatu(courseId);
        return result;
    }

    @ApiOperation("教师删除未通过课程")
    @PostMapping("/deleteReq")
    public ResultVO deleteReq(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
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
    public ResultVO updateChecked(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId,
                                  @Param("checked") @NotNull(message = "审核状态不能为空") Integer checked) {
        ResultVO result = courseService.updateChecked(courseId, checked);
        return result;
    }

    @ApiOperation("按照学科类别查看课程")
    @GetMapping("/getByCategory")
    public ResultVO getByCategory(@RequestParam("category") @NotNull(message = "学科类别不能为空") String category) {
        ResultVO result = courseService.getByCategory(category);
        return result;
    }

    @ApiOperation("按照学科名查看课程")
    @GetMapping("/getBySubName")
    public ResultVO getBySubName(@RequestParam("subName") @NotNull(message = "学科名不能为空") String subName) {
        ResultVO result = courseService.getBySubName(subName);
        return result;
    }

    @ApiOperation("按照课程名名称或作者模糊查询")
    @GetMapping("/queryFuzzy")
    public ResultVO queryByNameOrAuthorFuzzy(@RequestParam("name") @NotNull(message = "课程名或作者不能为空") String name) {
        ResultVO result = courseService.queryByNameOrAuthorFuzzy(name);
        return result;
    }

}
