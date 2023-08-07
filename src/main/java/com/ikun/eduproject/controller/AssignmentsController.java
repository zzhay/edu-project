package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.service.AssignmentsService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 */
@RestController
@RequestMapping("/assignment")
@CrossOrigin
@Api(value = "提供作业相关的接口", tags = "作业管理")
public class AssignmentsController {
    @Autowired
    private AssignmentsService assignmentsService;

    @ApiOperation("提交作业")
    @PostMapping("/submit")
    public ResultVO submit(@RequestBody @Valid Assignments assignments) {
        return assignmentsService.addAssignments(assignments);
    }

    @ApiOperation("批改作业")
    @PostMapping("/correcting")
    public ResultVO correcting(@RequestParam("assignment_id") @NotNull Integer assignmentId,@RequestParam("credit") @NotNull BigDecimal credit) {
        return assignmentsService.correctingAssignments(assignmentId, credit);
    }

    @ApiOperation("教师根据课程查看未批改作业")
    @GetMapping("/getByCourseIdNO")
    public ResultVO getByCourseIdNO(@RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseIdNO(courseId);
    }

    @ApiOperation("教师根据课程查看已批改作业")
    @GetMapping("/getByCourseIdOK")
    public ResultVO getByCourseIdOK(@RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseIdOK(courseId);
    }

    @ApiOperation("教师根据课程查看待批改作业数量")
    @GetMapping("/getNumByCourseIdNO")
    public ResultVO getNumByCourseIdON(@RequestParam("userId") @NotNull Integer userId) {
        return assignmentsService.getNumByCourseIdON(userId);
    }

    @ApiOperation("学生根据课程查看自己作业")
    @GetMapping("/getByCourseId")
    public ResultVO getByCourseId(@RequestParam("userId") @NotNull Integer userId,
                                  @RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseId(userId, courseId);
    }

    @ApiOperation("学生更新作业")
    @PostMapping("/updateAssignment")
    public ResultVO updateAssignment(@RequestBody @Valid Assignments assignments) {
        return assignmentsService.updateAssignment(assignments);
    }

}
