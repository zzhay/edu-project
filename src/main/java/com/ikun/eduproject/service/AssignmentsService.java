package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 * AssignmentsService提供了用作业相关功能的接口。
 * 定义了作业管理等相关功能的抽象方法。
 */
public interface AssignmentsService {

    /**
     * 提交作业
     * @param assignments 作业对象
     * @return ResultVO
     */
    ResultVO<String> addAssignments(Assignments assignments);

    /**
     * 批改作业
     * @param assignmentId 作业id
     * @param credit 给的kun分
     * @return ResultVO
     */
    ResultVO<String> correctingAssignments(Integer assignmentId, BigDecimal credit);

}
