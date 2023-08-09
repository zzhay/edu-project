package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Subject;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * SubjectDao是用于访问和操作学科数据的数据访问对象
 * 该类提供了对学科分类数据的操作。
 */
public interface SubjectDao {
    /**
     * 查询所有学科
     *
     * @return 学科类别集合
     */
    List<Subject> selectAll();

    /**
     * 根据学科名查询学科类别
     *
     * @param subName 学科名
     * @return 学科类别
     */
    String selectSubCategoryBySubName(String subName);
}
