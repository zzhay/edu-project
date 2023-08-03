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

}
