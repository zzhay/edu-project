package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.service.AssignmentsService;
import com.ikun.eduproject.vo.AssignmentNumVO;
import com.ikun.eduproject.vo.AssignmentVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 */
@RestController
@RequestMapping("/assignment")
@CrossOrigin
@Api(value = "提供作业相关的接口", tags = "作业管理")
public class AssignmentsController {
    @Resource
    private AssignmentsService assignmentsService;

    /**
     * 提交作业
     *
     * @param assignments 作业对象
     * @return ResultVO
     */
    @ApiOperation("提交作业")
    @PostMapping("/submit")
    public ResultVO<String> submit(@RequestBody @Valid Assignments assignments) {
        return assignmentsService.addAssignments(assignments);
    }

    /**
     * 批改作业
     *
     * @param assignmentId 作业id
     * @param credit       学分
     * @return ResultVO
     */
    @ApiOperation("批改作业")
    @PostMapping("/correcting")
    public ResultVO<String> correcting(@RequestParam("assignment_id") @NotNull Integer assignmentId, @RequestParam("credit") @NotNull BigDecimal credit) {
        return assignmentsService.correctingAssignments(assignmentId, credit);
    }

    /**
     * 教师根据课程查看未批改作业
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("教师根据课程查看未批改作业")
    @GetMapping("/getByCourseIdNO")
    public ResultVO<List<AssignmentVO>> getByCourseIdNO(@RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseIdNO(courseId);
    }

    /**
     * 教师根据课程查看已批改作业
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("教师根据课程查看已批改作业")
    @GetMapping("/getByCourseIdOK")
    public ResultVO<List<AssignmentVO>> getByCourseIdOK(@RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseIdOK(courseId);
    }

    /**
     * 教师根据课程查看待批改作业数量
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("教师根据课程查看待批改作业数量")
    @GetMapping("/getNumByCourseIdNO")
    public ResultVO<List<AssignmentNumVO>> getNumByCourseIdON(@RequestParam("userId") @NotNull Integer userId) {
        return assignmentsService.getNumByCourseIdON(userId);
    }

    /**
     * 学生根据课程查看自己作业
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("学生根据课程查看自己作业")
    @GetMapping("/getByCourseId")
    public ResultVO<Assignments> getByCourseId(@RequestParam("userId") @NotNull Integer userId,
                                               @RequestParam("courseId") @NotNull Integer courseId) {
        return assignmentsService.getByCourseId(userId, courseId);
    }

    /**
     * 学生更新作业
     *
     * @param assignments 作业对象
     * @return ResultVO
     */
    @ApiOperation("学生更新作业")
    @PostMapping("/updateAssignment")
    public ResultVO<String> updateAssignment(@RequestBody @Valid Assignments assignments) {
        return assignmentsService.updateAssignment(assignments);
    }

}
