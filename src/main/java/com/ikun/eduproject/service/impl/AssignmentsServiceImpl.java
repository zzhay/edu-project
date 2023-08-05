package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.AssignmentsDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.UpdateCreditException;
import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.service.AssignmentsService;
import com.ikun.eduproject.vo.AssignmentNumVO;
import com.ikun.eduproject.vo.AssignmentVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 * AssignmentsServiceImpl是作业相关功能的Service实现类。
 * 提供了作业管理相关功能的具体实现。
 */
@Service
public class AssignmentsServiceImpl implements AssignmentsService {
    @Autowired
    private AssignmentsDao assignmentsDao;
    @Autowired
    private UserDao userDao;

    /**
     * 提交作业
     *
     * @param assignments 作业对象
     * @return ResultVO
     */
    @Override
    public ResultVO<String> addAssignments(Assignments assignments) {
        //判断是否重复提交
        Assignments assignment = assignmentsDao.selectByUidAndCid(assignments.getUserId(), assignments.getCourseId());
        if (assignment != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "作业已提交过", null);
        }
        int i = assignmentsDao.insertAssignment(assignments);
        if (i > 0) {
            return new ResultVO<>(StatusVO.INSERT_OK, "提交成功", null);
        } else {
            return new ResultVO<>(StatusVO.INSERT_NO, "提交失败", null);
        }
    }

    /**
     * 批改作业
     *
     * @param assignmentId 作业id
     * @param credit       给的kun分
     * @return ResultVO
     */
    @Override
    public ResultVO<String> correctingAssignments(Integer assignmentId, BigDecimal credit) {
        //更新作业
        int i = assignmentsDao.updateAssignment(assignmentId, credit);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "批改失败", null);
        }
        //获取用户id
        Integer userId = assignmentsDao.selectByAssignmentId(assignmentId);
        try {
            //更新学分
            int i1 = userDao.updateCreditByUserId(userId, credit);
            if (i1 > 0) {
                return new ResultVO<>(StatusVO.UPDATE_OK, "批改成功", null);
            } else {
                throw new UpdateCreditException("kun分更新失败");
            }
        } catch (UpdateCreditException e) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "批改失败", null);
        }

    }

    /**
     * 教师根据课程查看未批改作业
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<AssignmentVO>> getByCourseIdNO(Integer courseId) {
        List<AssignmentVO> assignmentVOS = assignmentsDao.selectByCourseIdNO(courseId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", assignmentVOS);
    }

    /**
     * 教师根据课程查看已批改作业
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<AssignmentVO>> getByCourseIdOK(Integer courseId) {
        List<AssignmentVO> assignmentVOS = assignmentsDao.selectByCourseIdOK(courseId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", assignmentVOS);
    }

    /**
     * 教师根据课程查看待批改作业数量
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<AssignmentNumVO>> getNumByCourseIdON(Integer userId) {
        List<AssignmentNumVO> assignmentNumVOS = assignmentsDao.selectNumByUserId(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", assignmentNumVOS);
    }

    /**
     * 学生根据课程查看自己作业
     * @param userId 用户id
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<Assignments> getByCourseId(Integer userId, Integer courseId) {
        Assignments assignments = assignmentsDao.selectByUidAndCid(userId, courseId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", assignments);
    }
}
