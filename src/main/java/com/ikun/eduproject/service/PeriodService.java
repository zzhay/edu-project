package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Periods;
import com.ikun.eduproject.vo.ResultVO;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/14/014
 * PeriodService提供了课程课时相关功能的接口。
 * 定义了课程课时管理等相关功能的抽象方法。
 */
public interface PeriodService {
    /**
     * 根据课程id获取课时信息
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<List<Periods>> getByCourseId(Integer courseId);

}
