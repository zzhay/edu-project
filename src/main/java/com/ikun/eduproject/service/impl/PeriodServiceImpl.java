package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.PeriodDao;
import com.ikun.eduproject.pojo.Periods;
import com.ikun.eduproject.service.PeriodService;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/8/14/014
 * PeriodServiceImpl是课程课时相关功能的Service实现类。
 * 提供了课程课时管理等相关功能的具体实现。
 */
@Service
public class PeriodServiceImpl implements PeriodService {
    @Resource
    private PeriodDao periodDao;

    /**
     * 根据课程id获取课时信息
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<Periods>> getByCourseId(Integer courseId) {
        List<Periods> periods = periodDao.selectByCourseId(courseId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", periods);
    }
}
