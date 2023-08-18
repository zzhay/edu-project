package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Periods;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/14/014
 * PeriodDao是用于访问和操作课程课时数据的数据访问对象
 * 该类提供了对课程课时数据的增删改查操作。
 */
public interface PeriodDao {
    /**
     * 课时新增
     * @param periods 课时对象
     * @return int
     */
    int insert(Periods periods);

    /**
     * 修改课时
     * @param periods 课时对象
     * @return int
     */
    int update(Periods periods);

    /**
     * 根据课程id查询
     * @param courseId 课程id
     * @return List
     */
    List<Periods> selectByCourseId(Integer courseId);
}
