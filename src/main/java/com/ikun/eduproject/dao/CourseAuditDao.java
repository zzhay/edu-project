package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.CourseAudit;
import com.ikun.eduproject.pojo.User;
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
     * @param course
     * @return
     */
    int insertCourseAudit(Course course);

    /**
     * 更新课程审核版本信息
     *
     * @param course
     * @return
     */
    int updateCourseAudit(Course course);

    /**
     * 课程审核版本更新为课程表的信息
     *
     * @param course
     * @return
     */
    int updateFromCourse(Course course);

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
    Course selectByCoursrId(Integer courseId);

    /**
     * 更新审核
     * @param courseId
     * @param checked
     * @return
     */
    int updateCheck(@Param("courseId") Integer courseId, @Param("checked") Integer checked);

    /**
     * 更新课程状态
     *
     * @param courseId
     * @return
     */
    int updateStatu(Integer courseId);

}
