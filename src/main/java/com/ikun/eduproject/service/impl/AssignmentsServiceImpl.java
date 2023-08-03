package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.AssignmentsDao;
import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.service.AssignmentsService;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    /**
     * 提交作业
     * @param assignments 作业对象
     * @return ResultVO
     */
    @Override
    public ResultVO<String> addAssignments(Assignments assignments) {
        //判断是否重复提交
        Integer i = assignmentsDao.selectByUidAndCid(assignments.getUserId(), assignments.getCourseId());
        if (i != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "作业已提交过", null);
        }
        int i1 = assignmentsDao.insertAssignment(assignments);
        if (i1 > 0) {
            return new ResultVO<>(StatusVO.INSERT_OK, "提交成功", null);
        } else {
            return new ResultVO<>(StatusVO.INSERT_NO, "提交失败", null);
        }
    }

    @Override
    public ResultVO<String> correctingAssignments(Integer assignmentId, BigDecimal credit) {
        int i = assignmentsDao.updateAssignment(assignmentId, credit);
        if (i > 0) {
            return new ResultVO<>(StatusVO.UPDATE_OK, "批改成功", null);
        } else {
            return new ResultVO<>(StatusVO.UPDATE_NO, "批改失败", null);
        }
    }
}
