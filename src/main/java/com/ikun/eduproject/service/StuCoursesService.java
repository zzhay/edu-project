package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.StudentCourse;
import com.ikun.eduproject.vo.LoginVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * UserService提供了学生的课程相关功能的接口。
 * 定义了学生课程管理等相关功能的抽象方法。
 */
public interface StuCoursesService {
    /**
     * 购买课程
     * @param studentCourse 购买课程信息
     * @return ResultVO
     */
    ResultVO<String> buyCourse(StudentCourse studentCourse);

    /**
     * 购买时验证密码
     * @param loginVO 登录信息
     * @return ResultVO
     */
    ResultVO<String> checkPwd(LoginVO loginVO);

    /**
     * 获取购买的课程
     * @param userId 用户id
     * @return ResultVO
     */
    ResultVO<List<Course>> getOwnCourse(Integer userId);
}
