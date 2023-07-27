package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * CourseServiceImpl是课程相关功能的Service实现类。
 * 提供了课程管理等相关功能的具体实现。
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    /**
     * 新增课程
     *
     * @param course
     * @return
     */
    @Override
    public ResultVO addCourse(Course course) {
        //根据教师id和课程名查出课程id
        Integer courseId = courseDao.selectByUIdAndName(course);
        //如果id存在，则该教师名下已有该课程名称，报冲突
        if (courseId != null) {
            return new ResultVO(StatusVo.INSERT_NO_COURSE_NAME, "课程名已经存在", null);
        }
        //新增课程
        int i = courseDao.insertCourse(course);
        if (i > 0) {
            return new ResultVO(StatusVo.INSERT_OK, "添加成功", null);
        } else {
            return new ResultVO(StatusVo.INSERT_NO, "添加失败", null);
        }
    }

    /**
     * 教师查看名下课程
     *
     * @param userId
     * @return
     */
    @Override
    public ResultVO getOwnCourse(int userId) {
        List<Course> list = courseDao.selectByUserId(userId);
        return new ResultVO(StatusVo.SELECT_OK, "查询成功", list);

    }

    /**
     * 教师更新课程信息
     *
     * @param course
     * @return
     */
    @Override
    public ResultVO updateCourse(Course course) {
        //根据教师id和课程名查出课程id
        Integer courseId = courseDao.selectByUIdAndName(course);
        //判断课程名是否冲突
        if (courseId != null && courseId != course.getCourseId()) {
            return new ResultVO(StatusVo.INSERT_NO_COURSE_NAME, "课程名已经存在", null);
        } else {
            int i = courseDao.updateCourse(course);
            if (i > 0) {
                return new ResultVO(StatusVo.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO(StatusVo.UPDATE_NO, "更新失败", null);
            }
        }
    }


}
