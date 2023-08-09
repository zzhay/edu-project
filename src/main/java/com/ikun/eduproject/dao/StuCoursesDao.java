package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.StudentCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * StuCoursesDao是用于访问和操作学生的课程数据的数据访问对象
 * 该类提供了对学生的课程数据的增删改查操作。
 */
public interface StuCoursesDao {

    /**
     * 添加学生课程信息
     *
     * @param studentCourse 学生课程信息
     * @return int
     */
    Integer insertStuCourse(StudentCourse studentCourse);

    /**
     * 根据用户id和课程id查询
     *
     * @param userId   用户id
     * @param courseId 课程id
     * @return 学生的课程id
     */
    Integer selectByUidAndCid(@Param("userId") Integer userId, @Param("courseId") Integer courseId);

    /**
     * 按照用户id查询车购买的课程
     *
     * @param userId 用户id
     * @return 课程集合
     */
    List<Course> selectByUid(Integer userId);
}
