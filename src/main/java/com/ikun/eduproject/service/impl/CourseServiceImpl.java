package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
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
            return new ResultVO(StatusVO.INSERT_NO_COURSE_NAME, "课程名已经存在", null);
        }
        //新增课程
        int i = courseDao.insertCourse(course);
        if (i > 0) {
            return new ResultVO(StatusVO.INSERT_OK, "添加成功", null);
        } else {
            return new ResultVO(StatusVO.INSERT_NO, "添加失败", null);
        }
    }

    /**
     * 教师查看已上架课程
     *
     * @param userId
     * @return
     */
    @Override
    public ResultVO getCourse1(Integer userId) {
        if (userId != null) {
            List<Course> list = courseDao.selectByUserId1(userId);
            return new ResultVO(StatusVO.SELECT_OK, "查询成功", list);
        } else {
            return new ResultVO(StatusVO.SELECT_NO, "id为空", null);
        }
    }

    /**
     * 教师查看待审核课程
     *
     * @param userId
     * @return
     */
    @Override
    public ResultVO getCourse2(Integer userId) {
        if (userId != null) {
            List<Course> list = courseDao.selectByUserId2(userId);
            return new ResultVO(StatusVO.SELECT_OK, "查询成功", list);
        } else {
            return new ResultVO(StatusVO.SELECT_NO, "id为空", null);
        }
    }

    /**
     * 教师查看审核未通过课程
     *
     * @param userId
     * @return
     */
    @Override
    public ResultVO getCourse3(Integer userId) {
        if (userId != null) {
            List<Course> list = courseDao.selectByUserId3(userId);
            return new ResultVO(StatusVO.SELECT_OK, "查询成功", list);
        } else {
            return new ResultVO(StatusVO.SELECT_NO, "id为空", null);
        }
    }

    /**
     * 教师查看已下架课程
     *
     * @param userId
     * @return
     */
    @Override
    public ResultVO getCourse4(Integer userId) {
        if (userId != null) {
            List<Course> list = courseDao.selectByUserId4(userId);
            return new ResultVO(StatusVO.SELECT_OK, "查询成功", list);
        } else {
            return new ResultVO(StatusVO.SELECT_NO, "id为空", null);
        }
    }

    /**
     * 教师修改课程信息
     *
     * @param course
     * @return
     */
    @Override
    public ResultVO updateCourse(Course course) {
        //根据教师id和课程名查出课程id
        Integer courseId = courseDao.selectByUIdAndName(course);
        //判断课程名是否冲突: courseId不为空说明该课程名已存在，courseId不相等说明不是同一课程
        if (courseId != null && !courseId.equals(course.getCourseId())) {
            return new ResultVO(StatusVO.INSERT_NO_COURSE_NAME, "课程名已经存在", null);
        } else {
            //更新课程
            int i = courseDao.updateCourse(course);
            if (i > 0) {
                return new ResultVO(StatusVO.UPDATE_OK, "更新成功", null);
            } else {
                return new ResultVO(StatusVO.UPDATE_NO, "更新失败", null);
            }
        }
    }

    /**
     * 教师下架课程
     * @param courseId
     * @return
     */
    @Override
    public ResultVO updateStatu(String courseId) {
        int i = courseDao.updateStatu(courseId);
        if (i > 0) {
            return new ResultVO(StatusVO.UPDATE_OK, "下架成功", null);
        } else {
            return new ResultVO(StatusVO.UPDATE_NO, "下架失败", null);
        }
    }

    /**
     * 教师删除申请
     *
     * @param courseId
     * @return
     */
    @Override
    public ResultVO deleteReq(String courseId) {
        int i = courseDao.deleteCourse(courseId);
        if (i > 0) {
            return new ResultVO(StatusVO.UPDATE_OK, "下架成功", null);
        } else {
            return new ResultVO(StatusVO.UPDATE_NO, "下架失败", null);
        }
    }

    /**
     * 查看所有待审核课程
     * @return
     */
    @Override
    public ResultVO getChecked() {
        List<GetCourseCheckedVO> lists = courseDao.selectAllChecked();
        return new ResultVO(StatusVO.SELECT_OK, "查询成功", lists);
    }

    /**
     * 课程审核
     * @param courseId
     * @param checked
     * @return
     */
    @Override
    public ResultVO updateChecked(Integer courseId, Integer checked) {
        int i = courseDao.updateChecked(courseId, checked);
        if (i > 0) {
            return new ResultVO(StatusVO.UPDATE_OK, "更新成功", null);
        } else {
            return new ResultVO(StatusVO.UPDATE_NO, "更新失败", null);
        }
    }

    /**
     * 按照课程类别查看课程
     * @param category
     * @return
     */
    @Override
    public ResultVO getByCategory(String category) {
        //判断参数是否为空
        if (category == null) {
            return new ResultVO(StatusVO.SELECT_NO, "参数为空", null);
        } else {
            //查询
            List<Course> courses = courseDao.selectByCategory(category);
            return new ResultVO(StatusVO.SELECT_OK, "查询成功", courses);
        }
    }

}
