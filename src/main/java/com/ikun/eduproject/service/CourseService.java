package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * UserService提供了课程相关功能的接口。
 * 定义了课程管理等相关功能的抽象方法。
 */
public interface CourseService {

    /**
     * 新增课程
     * @param course
     * @return
     */
    ResultVO addCourse(Course course);

    /**
     * 教师查看已上架课程
     * @param userId
     * @return
     */
    ResultVO getCourse1(Integer userId);

    /**
     * 教师查看待审核课程
     * @param userId
     * @return
     */
    ResultVO getCourse2(Integer userId);

    /**
     * 教师查看审核未通过课程
     * @param userId
     * @return
     */
    ResultVO getCourse3(Integer userId);

    /**
     * 教师修改课程信息
     * @param course
     * @return
     */
    ResultVO updateCourse(Course course);

    /**
     * 教师下架课程
     * @param name
     * @return
     */
    ResultVO updateStatu(String name);

    /**
     * 查看所有待审核课程
     * @return
     */
    ResultVO getAllChecked();

    /**
     * 课程审核
     * @param courseId
     * @param checked
     * @return
     */
    ResultVO updateChecked(Integer courseId, @Param("checked") Integer checked);
}
