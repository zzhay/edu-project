package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.Periods;
import com.ikun.eduproject.pojo.StudentCourse;
import com.ikun.eduproject.service.PeriodService;
import com.ikun.eduproject.service.StuCoursesService;
import com.ikun.eduproject.vo.LoginVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/period")
@CrossOrigin
@Api(value = "提供课程课时管理的接口", tags = "课程课时管理")
public class PeriodController {

    @Resource
    private PeriodService periodService;

    /**
     * 显示课程的课时信息
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("显示课程的课时信息")
    @GetMapping("/getCoursePeriods")
    public ResultVO<List<Periods>> getOwnCourse(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
        return periodService.getByCourseId(courseId);
    }
}
