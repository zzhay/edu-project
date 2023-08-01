package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.CourseAudit;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/1/001
 */
public interface CourseAuditDao {
    /**
     * 新增课程审核版本
     *
     * @param courseAudit
     * @return
     */
    int insertCourseAudit(CourseAudit courseAudit);

    /**
     * 更新课程审核版本信息
     *
     * @param courseAudit
     * @return
     */
    int updateCourseAudit(CourseAudit courseAudit);

    /**
     * 按照课程id删除
     * @param courseId
     * @return
     */
    int deleteCourseAudit(Integer courseId);

    /**
     * 按照教师id和课程名查询课程id
     *
     * @param userId
     * @param name
     * @return
     */
    Integer selectByUIdAndName(@Param("userId") Integer userId, @Param("name")String name);

    /**
     * 按照教师id查询待审核课程
     *
     * @param userId
     * @return 课程集合
     */
    List<CourseAudit> selectByUserIdChecking(Integer userId);

    /**
     * 按照教师id查询审核未通过课程
     *
     * @param userId
     * @return 课程集合
     */
    List<CourseAudit> selectByUserIdCheckNo(Integer userId);

    /**
     * 查询所有待审核课程
     *
     * @return
     */
    List<GetCourseCheckedVO> selectAllCheck();

    /**
     * 按照课程id查询课程
     * @param courseId
     * @return
     */
    List<GetCourseCheckedVO> selectByCoursrId(Integer courseId);

}
