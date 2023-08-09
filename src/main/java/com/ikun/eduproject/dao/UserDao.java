package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.ChangeInfoVO;
import com.ikun.eduproject.vo.ChangePwdVO;
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
     *
     * @param username 用户名
     * @return User对象
     */
    User selectByUsername(String username);

    /**
     * 新增用户
     *
     * @param user 用户对象
     * @return int
     */
    int insertUser(User user);

    /**
     * 按照username更新基础信息
     *
     * @param changeInfoVO 更新信息对象
     * @return int
     */
    int updateByUsername(ChangeInfoVO changeInfoVO);

    /**
     * 更新密码
     *
     * @param changePwdVO 更新密码对象
     * @return int
     */
    int updatePassword(ChangePwdVO changePwdVO);

    /**
     * 查出所有学生
     *
     * @return 学生集合
     */
    List<User> selectStudent();

    /**
     * 查出所有老师
     *
     * @return 教师集合
     */
    List<User> selectTeacher();

    /**
     * 查出所有待审核老师
     *
     * @return 待审核教师集合
     */
    List<User> selectTeacherNo();

    /**
     * 根据邮箱查找
     *
     * @param email 邮箱
     * @return User对象
     */
    User selectByEmail(String email);

    /**
     * 按照username修改状态
     *
     * @param username 用户名
     * @param statu    状态
     * @return int
     */
    int updateStatu(@Param("username") String username, @Param("statu") Integer statu);

    /**
     * 按照username更新头像
     *
     * @param username 用户名
     * @param url      url地址
     * @return int
     */
    int updateImage(@Param("username") String username, @Param("url") String url);

    /**
     * 删除用户
     *
     * @param username 用户名
     * @return int
     */
    int deleteByUsername(String username);

    /**
     * 按照用户id查看
     *
     * @param userId 用户id
     * @return User
     */
    User selectByUserId(Integer userId);

    /**
     * 按照用户id更新学分
     *
     * @param userId 用户id
     * @param credit 学分
     * @return int
     */
    int updateCreditByUserId(@Param("userId") Integer userId, @Param("credit") BigDecimal credit);
}
