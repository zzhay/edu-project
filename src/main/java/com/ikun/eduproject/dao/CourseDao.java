package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
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
     * @param course
     * @return
     */
    int insertCourse(Course course);

    /**
     * 按照教师id和课程名查询课程id
     *
     * @param course
     * @return
     */
    Integer selectByUIdAndName(Course course);

    /**
     * 按照教师id查询已上架课程
     *
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId1(Integer userId);

    /**
     * 按照教师id查询待审核课程
     *
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId2(Integer userId);

    /**
     * 按照教师id查询审核未通过课程
     *
     * @param userId
     * @return 课程集合
     */
    List<Course> selectByUserId3(Integer userId);

    /**
     * 教师查看已下架课程
     * @param userId
     * @return
     */
    List<Course> selectByUserId4(Integer userId);

    /**
     * 更新课程信息
     *
     * @param course
     * @return
     */
    int updateCourse(Course course);

    /**
     * 更新课程状态
     *
     * @param courseId
     * @return
     */
    int updateStatu(Integer courseId);

    /**
     * 删除课程
     *
     * @param courseId
     * @return
     */
    int deleteCourse(Integer courseId);

    /**
     * 查询所有待审核课程
     *
     * @return
     */
    List<GetCourseCheckedVO> selectAllChecked();

    /**
     * 更新审核
     *
     * @param courseId
     * @param checked
     * @return
     */
    int updateChecked(@Param("courseId") Integer courseId, @Param("checked") Integer checked);

    /**
     * 按照课程id查询
     * @param courseId 课程id
     * @return Course
     */
    Course selectByCourseId(Integer courseId);

    /**
     * 查询已上架课程
     * @return List<Course>
     */
    List<Course> selectAvailable();

    /**
     * 按照学科类别查询
     * @param category 学科类别
     * @return List
     */
    List<Course> selectByCategory(String category);

    /**
     * 按照学科名查询
     * @param subName subName
     * @return List
     */
    List<Course> selectBySubName(String subName);

    /**
     * 根据课程id查看价格
     * @param courseId 课程id
     * @return BigDecimal
     */
    BigDecimal selectPriceByCourseId(Integer courseId);

}
