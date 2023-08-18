package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.StuCoursesDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.error.UpdateCreditException;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.StudentCourse;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.service.StuCoursesService;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.MD5Utils;
import com.ikun.eduproject.vo.EmailMsgVO;
import com.ikun.eduproject.vo.LoginVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * StuCoursesServiceImpl是学生课程相关功能的Service实现类。
 * 提供了学生课程管理等相关功能的具体实现。
 */
@Slf4j
@Service
public class StuCoursesServiceImpl implements StuCoursesService {
    @Resource
    private StuCoursesDao stuCoursesDao;
    @Resource
    private CourseDao courseDao;
    @Resource
    private UserDao userDao;

    @Resource
    private EmailUtil emailUtil;

    /**
     * 购买课程
     *
     * @param studentCourse 购买课程信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = UpdateCreditException.class)
    public ResultVO<String> buyCourse(StudentCourse studentCourse) {
        //判断是否已经购买
        Integer i = stuCoursesDao.selectByUidAndCid(studentCourse.getUserId(), studentCourse.getCourseId());
        if (i != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "已购买过该课程", null);
        }
        //课程价格
        BigDecimal price = courseDao.selectPriceByCourseId(studentCourse.getCourseId());
        //用户学分
        User user = userDao.selectByUserId(studentCourse.getUserId());
        //判断学分是否足够
        if (user.getCredit().compareTo(price) < 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "学分不足，购买失败", null);
        }
        //添加学生课程
        Integer integer = stuCoursesDao.insertStuCourse(studentCourse);
        if (integer <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "购买失败", null);
        }
        //学生购买后的学分
        BigDecimal subtract = user.getCredit().subtract(price);
        //更新学分
        try {
            int i1 = userDao.updateCreditByUserId(studentCourse.getUserId(), subtract);
            if (i1 <= 0) {
                throw new UpdateCreditException("更新学分失败");
            }
        } catch (UpdateCreditException e) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "购买失败", null);
        }
        //获取课程名
        String name = courseDao.selectByCourseId(studentCourse.getCourseId()).getName();
        //发送邮件通知
        emailUtil.sendMessage(user.getEmail(), EmailMsgVO.BUYCOURSE, EmailMsgVO.buyCourse(user.getUsername(), name));
        return new ResultVO<>(StatusVO.UPDATE_OK, "购买成功", null);

    }

    /**
     * 购买时验证密码
     *
     * @param loginVO 登录信息
     * @return ResultVO
     */
    @Override
    public ResultVO<String> checkPwd(LoginVO loginVO) {
        //根据用户名获取用户信息
        User user = userDao.selectByUsername(loginVO.getUsername());
        if (user == null) {
            return new ResultVO<>(StatusVO.SELECT_NO, "用户名或密码错误", null);
        }
        //验证密码是否相同
        String md5 = MD5Utils.md5(loginVO.getPassword());
        if (Objects.equals(md5, user.getPassword())) {
            return new ResultVO<>(StatusVO.SELECT_OK, "密码正确", null);
        } else {
            return new ResultVO<>(StatusVO.SELECT_NO, "密码错误", null);
        }
    }

    /**
     * 获取购买的课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<Course>> getOwnCourse(Integer userId) {
        List<Course> courses = stuCoursesDao.selectByUid(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", courses);
    }
}
