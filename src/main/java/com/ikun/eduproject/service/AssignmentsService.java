package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.vo.AssignmentNumVO;
import com.ikun.eduproject.vo.AssignmentVO;
import com.ikun.eduproject.vo.ResultVO;
import java.math.BigDecimal;
import java.util.List;

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

    /**
     * 教师根据课程查看未批改作业
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<List<AssignmentVO>> getByCourseIdNO(Integer courseId);

    /**
     * 教师根据课程查看已批改作业
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<List<AssignmentVO>> getByCourseIdOK(Integer courseId);

    /**
     * 教师根据课程查看待批改作业数量
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<AssignmentNumVO>> getNumByCourseIdON(Integer userId);

    /**
     * 学生根据课程查看自己作业
     * @param userId 用户id
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<Assignments> getByCourseId(Integer userId,Integer courseId);

    /**
     * 学生更新作业
     * @param assignments 作业
     * @return ResultVO
     */
    ResultVO<String> updateAssignment(Assignments assignments);
}
