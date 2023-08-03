package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Assignments;
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

    //添加作业
    int insertAssignment(Assignments assignments);

    //根据学生id和课程id查询作业id
    Integer selectByUidAndCid(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    //更新作业
    int updateAssignment(@Param("assignmentId") Integer assignmentId, @Param("credit") BigDecimal credit);
}
