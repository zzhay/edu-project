package com.ikun.eduproject.service;

import com.ikun.eduproject.vo.ResultVO;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * SubjectService提供了学科相关功能的接口。
 * 定义了学科分类管理等相关功能的抽象方法。
 */
public interface SubjectService {
    /**
     * 获取所有学科
     * @return
     */
    ResultVO getAll();
}
