package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.vo.ResultVO;

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
     * 教师查看名下课程
     * @param userId
     * @return
     */
    ResultVO getOwnCourse(int userId);

}
