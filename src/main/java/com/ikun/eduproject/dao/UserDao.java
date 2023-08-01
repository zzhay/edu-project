package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 * UserDao是用于访问和操作用户数据的数据访问对象
 * 该类提供了对用户数据的增删改查操作。
 */
public interface UserDao {
    /**
     * 按照用户名查询
     * @param username
     * @return User对象
     */
    User selectByUsername(String username);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 按照username更新基础信息
     * @param changeInfoVO
     * @return
     */
    int updateByUsername(ChangeInfoVO changeInfoVO);

    /**
     * 更新密码
     * @param changePwdVO
     * @return
     */
    int updatePassword(ChangePwdVO changePwdVO);

    /**
     * 查出所有学生
     * @return
     */
    List<User> selectStudent();

    /**
     * 查出所有老师
     * @return
     */
    List<User> selectTeacher();

    /**
     * 查出所有待审核老师
     * @return
     */
    List<User> selectTeacherNo();

    /**
     * 根据手机号查找
     * @param phone
     * @return
     */
    Integer selectByPhone(String phone);

    /**
     * 根据邮箱查找
     * @param email
     * @return
     */
    User selectByEmail(String email);

    /**
     * 按照username修改状态
     * @param username
     * @return
     */
    int updateStatu(@Param("username") String username,@Param("statu") Integer statu);

    /**
     * 按照username更新头像
     * @param username
     * @param url
     * @return
     */
    int updateImage(@Param("username") String username, @Param("url") String url);

    /**
     * 删除用户
     * @param username
     * @return
     */
    int deleteByUsername(String username);

    /**
     * 按照用户id查看学分
     * @param userId 用户id
     * @return BigDecimal
     */
    BigDecimal selectCreditByUserId(Integer userId);

    /**
     * 按照用户id更新学分
     * @param userId 用户id
     * @return int
     */
    int updateCreditByUserId(@Param("userId") Integer userId,@Param("credit") BigDecimal credit);
}
