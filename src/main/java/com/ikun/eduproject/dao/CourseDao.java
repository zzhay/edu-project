package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;

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
     * 按照教师id查询课程
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId(int userId);

    /**
     * 按照教师id和课程名查询课程
     * @param course
     * @return
     */
    Integer selectByUIdAndName(Course course);

    /**
     * 编辑课程
     * @param course
     * @return
     */
    int updateCourse(Course course);
}
