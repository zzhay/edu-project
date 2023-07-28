package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.vo.GetCourseChecked;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * CourseDao是用于访问和操作课程数据的数据访问对象
 * 该类提供了对课程数据的增删改查操作。
 */
public interface CourseDao {
    /**
     * 添加课程
     * @param course
     * @return
     */
    int insertCourse(Course course);

    /**
     * 按照教师id和课程名查询课程id
     * @param course
     * @return
     */
    Integer selectByUIdAndName(Course course);

    /**
     * 按照教师id查询已上架课程
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId1(Integer userId);

    /**
     * 按照教师id查询待审核课程
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId2(Integer userId);

    /**
     * 按照教师id查询审核未通过课程
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId3(Integer userId);

    /**
     * 更新课程信息
     * @param course
     * @return
     */
    int updateCourse(Course course);

    /**
     * 修改课程状态
     * @param name
     * @return
     */
    int updateStatu(String name);

    /**
     * 查询所有待审核课程
     * @return
     */
    List<GetCourseChecked> selectAllChecked();

    /**
     * 更新审核
     * @param courseId
     * @return
     */
    int updateChecked(Integer courseId);
}
