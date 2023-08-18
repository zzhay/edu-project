package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.AssignmentsDao;
import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.AliOSSDeleteException;
import com.ikun.eduproject.error.UpdateCreditException;
import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.AssignmentsService;
import com.ikun.eduproject.utils.AliOssUtils;
import com.ikun.eduproject.vo.AssignmentNumVO;
import com.ikun.eduproject.vo.AssignmentVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 * AssignmentsServiceImpl是作业相关功能的Service实现类。
 * 提供了作业管理相关功能的具体实现。
 */
@Service
public class AssignmentsServiceImpl implements AssignmentsService {
    @Resource
    private AssignmentsDao assignmentsDao;
    @Resource
    private UserDao userDao;
    @Resource
    private CourseDao courseDao;

    @Resource
    private AliOssUtils aliOssUtils;

    /**
     * 提交作业
     *
     * @param assignments 作业对象
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> addAssignments(Assignments assignments) {
        //判断是否重复提交
        Assignments assignment1 = assignmentsDao.selectByUidAndCid(assignments.getUserId(),
                assignments.getCourseId(), assignments.getPeriods());
        if (assignment1 != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "当前课时作业已提交过", null);
        }
        //新增
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
    @Transactional(rollbackFor = UpdateCreditException.class)
    public ResultVO<String> correctingAssignments(Integer assignmentId, BigDecimal credit) {
        //更新作业
        int i = assignmentsDao.updateAssignmentStatu(assignmentId, credit);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "批改失败", null);
        }
        //获取用户id
        Integer userId = assignmentsDao.selectByAssignmentId(assignmentId);
        try {
            //更新学分
            User user = userDao.selectByUserId(userId);
            BigDecimal credit1 = user.getCredit().add(credit);
            int i1 = userDao.updateCreditByUserId(userId, credit1);
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
    public ResultVO<Map<Integer, List<AssignmentVO>>> getByCourseIdNO(Integer courseId) {
        Map<Integer, List<AssignmentVO>> map = new HashMap<>();
        //获取总课时
        Integer periodAll = courseDao.selectPeriodAll(courseId);
        for (Integer i = 1; i <= periodAll; i++) {
            //查询该课时下的作业
            List<AssignmentVO> assignmentVOS = assignmentsDao.selectByCourseIdAndPeriodNO(courseId, i);
            map.put(i, assignmentVOS);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", map);
    }

    /**
     * 教师根据课程查看已批改作业
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<Map<Integer, List<AssignmentVO>>> getByCourseIdOK(Integer courseId) {
        Map<Integer, List<AssignmentVO>> map = new HashMap<>();
        //获取总课时
        Integer periodAll = courseDao.selectPeriodAll(courseId);
        for (Integer i = 1; i <= periodAll; i++) {
            //查询该课时下的作业
            List<AssignmentVO> assignmentVOS = assignmentsDao.selectByCourseIdAndPeriodOK(courseId, i);
            map.put(i, assignmentVOS);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", map);
    }

    /**
     * 教师根据课程查看待批改作业数量
     *
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
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<Map<Integer, Assignments>> getByCourseId(Integer userId, Integer courseId) {
        Map<Integer, Assignments> map = new HashMap<>();
        //总课时
        Integer periodAll = courseDao.selectPeriodAll(courseId);
        for (int i = 1; i <= periodAll; i++) {
            //当前课时的作业
            Assignments assignments = assignmentsDao.selectByUidAndCid(userId, courseId,i);
            map.put(i, assignments);
        }

        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", map);
    }

    /**
     * 学生更新作业
     *
     * @param assignments 作业
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> updateAssignment(Assignments assignments) {
        //查出原作业信息
        Assignments assignments1 = assignmentsDao.selectByUidAndCidAndPeriod(assignments.getUserId(),
                assignments.getCourseId(), assignments.getPeriods());
        //判断作业是否批改
        if (assignments1.getStatu().equals(1)) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "作业已批改，无法修改", null);
        }
        //更新作业
        assignments.setAssignmentId(assignments1.getAssignmentId());
        int i = assignmentsDao.updateAssignment(assignments);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "修改失败", null);
        }
        try {
            //删除aliOSS中原文件
            boolean b = aliOssUtils.deleteImageByUrl(assignments1.getAssignmentUrl());
            if (!b) {
                throw new AliOSSDeleteException("原文件删除失败");
            }
        } catch (AliOSSDeleteException e) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "修改失败", null);
        }
        return new ResultVO<>(StatusVO.UPDATE_OK, "修改成功", null);
    }
}
