package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     *
     * @param course 课程对象
     * @return int
     */
    int insertCourse(Course course);

    /**
     * 按照教师id和课程名查询课程id
     *
     * @param userId 教师id
     * @param name 课程名
     * @return 课程id
     */
    Integer selectByUIdAndName(@Param("userId") Integer userId, @Param("name")String name);

    /**
     * 按照教师id查询已上架课程
     *
     * @param userId 教师id
     * @return 课程集合
     */
    List<Course> selectByUserId(Integer userId);

    /**
     * 教师查看已下架课程
     * @param userId 教师id
     * @return 课程集合
     */
    List<Course> selectByUserId2(Integer userId);

    /**
     * 更新课程信息
     *
     * @param course 课程对象
     * @return int
     */
    int updateCourse(Course course);

    /**
     * 更新课程状态
     *
     * @param courseId 课程id
     * @return int
     */
    int updateStatu(Integer courseId);

    /**
     * 删除课程
     *
     * @param courseId 课程id
     * @return int
     */
    int deleteCourse(Integer courseId);

    /**
     * 按照课程id查询
     * @param courseId 课程id
     * @return Course
     */
    Course selectByCourseId(Integer courseId);

    /**
     * 按照学科类别查询
     * @param category 学科类别
     * @return 课程集合
     */
    List<Course> selectByCategory(String category);

    /**
     * 按照学科名查询
     * @param subName subName
     * @return 课程集合
     */
    List<Course> selectBySubName(String subName);

    /**
     * 根据课程id查看价格
     * @param courseId 课程id
     * @return 价格
     */
    BigDecimal selectPriceByCourseId(Integer courseId);

    /**
     * 根据课程id查询用户邮箱
     * @param courseId 课程id
     * @return 用户邮箱
     */
    String selectEmailByCourseId(Integer courseId);

    /**
     * 按课程id查询总课时
     * @param courseId 课程id
     * @return Integer
     */
    Integer selectPeriodAll(Integer courseId);

}
