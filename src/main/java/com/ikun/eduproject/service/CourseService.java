package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.ElasticsearchCourse;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import com.ikun.eduproject.vo.ResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * UserService提供了课程相关功能的接口。
 * 定义了课程管理等相关功能的抽象方法。
 */
public interface CourseService {

    /**
     * 新增课程
     *
     * @param course 课程信息
     * @return ResultVO
     */
    ResultVO<String> addCourse(Course course);

    /**
     * 教师查看已上架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<Course>> getCourse1(Integer userId);

    /**
     * 教师查看待审核课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<Course>> getCourse2(Integer userId);

    /**
     * 教师查看审核未通过课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<Course>> getCourse3(Integer userId);

    /**
     * 教师查看已下架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<Course>> getCourse4(Integer userId);

    /**
     * 教师修改课程信息
     *
     * @param course 课程信息
     * @return ResultVO
     */
    ResultVO<String> updateCourse(Course course);

    /**
     * 教师下架课程
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<String> updateStatu(Integer courseId);

    /**
     * 教师删除申请
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<String> deleteReq(Integer courseId);


    /**
     * 查看所有待审核课程
     *
     * @return ResultVO
     */
    ResultVO<List<GetCourseCheckedVO>> getChecked();

    /**
     * 课程审核
     *
     * @param courseId 课程id
     * @param checked  审核状态
     * @return ResultVO
     */
    ResultVO<String> updateChecked(@Param("courseId") Integer courseId, @Param("checked") Integer checked);

    /**
     * 按照学科类别查看课程
     * @param category 学科类别
     * @return ResultVO
     */
    ResultVO<List<Course>> getByCategory(String category);

    /**
     * 按照学科名查询
     * @param subName 学科名
     * @return ResultVO
     */
    ResultVO<List<Course>> getBySubName(String subName);

    /**
     * 按照名称或作者模糊查询
     * @param name 课程名或作者
     * @return ResultVO
     */
    ResultVO<List<ElasticsearchCourse>> queryByNameOrAuthorFuzzy(String name);
}
