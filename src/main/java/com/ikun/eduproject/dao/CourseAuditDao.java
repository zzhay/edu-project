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
     * @param course 课程对象
     * @return int
     */
    int insertCourseAudit(Course course);

    /**
     * 更新课程审核版本信息
     *
     * @param course 课程对象
     * @return int
     */
    int updateCourseAudit(Course course);

    /**
     * 课程审核版本更新为课程表的信息
     *
     * @param course 课程对象
     * @return int
     */
    int updateFromCourse(Course course);

    /**
     * 按照课程id删除
     * @param courseId 课程id
     * @return int
     */
    int deleteCourseAudit(Integer courseId);

    /**
     * 按照教师id和课程名查询课程id
     *
     * @param userId 教师id
     * @param name 课程名
     * @return 课程id
     */
    Integer selectByUIdAndName(@Param("userId") Integer userId, @Param("name")String name);

    /**
     * 按照教师id查询待审核课程
     *
     * @param userId 教师id
     * @return 课程集合
     */
    List<CourseAudit> selectByUserIdChecking(Integer userId);

    /**
     * 按照教师id查询审核未通过课程
     *
     * @param userId 教师id
     * @return 课程集合
     */
    List<CourseAudit> selectByUserIdCheckNo(Integer userId);

    /**
     * 查询所有待审核课程
     *
     * @return 课程集合
     */
    List<GetCourseCheckedVO> selectAllCheck();

    /**
     * 按照课程id查询课程
     * @param courseId 课程id
     * @return Course
     */
    Course selectByCoursrId(Integer courseId);

    /**
     * 更新审核
     * @param courseId 课程id
     * @param checked 审核情况
     * @return int
     */
    int updateCheck(@Param("courseId") Integer courseId, @Param("checked") Integer checked);

    /**
     * 更新课程状态
     *
     * @param courseId 课程id
     * @return int
     */
    int updateStatu(Integer courseId);

}
