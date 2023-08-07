package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.vo.AssignmentNumVO;
import com.ikun.eduproject.vo.AssignmentVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 */
public interface AssignmentsDao {
    //根据课程id查作业
    List<Assignments> selectByCourseId(Integer courseId);

    //根据作业id查userId
    Integer selectByAssignmentId(Integer assignmentId);

    //添加作业
    int insertAssignment(Assignments assignments);

    //根据学生id和课程id查询作业
    Assignments selectByUidAndCid(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    //更新作业状态
    int updateAssignmentStatu(@Param("assignmentId") Integer assignmentId, @Param("credit") BigDecimal credit);

    //根据课程id获取未批改作业
    List<AssignmentVO> selectByCourseIdNO(Integer courseId);

    //根据课程id获取已批改作业
    List<AssignmentVO> selectByCourseIdOK(Integer courseId);

    //根据用户id查看未审批作业数量
    List<AssignmentNumVO> selectNumByUserId(Integer userId);

    //更新作业
    int updateAssignment(Assignments assignments);

}
