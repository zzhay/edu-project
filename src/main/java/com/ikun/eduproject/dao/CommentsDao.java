package com.ikun.eduproject.dao;

import com.ikun.eduproject.pojo.Comments;
import com.ikun.eduproject.vo.CommentsVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * CommentsDao是用于访问和操作评论数据的数据访问对象
 * 该类提供了对课程评论数据的增删改查操作。
 */
public interface CommentsDao {
    //新增评论
    int insertComment(Comments comments);

    //按照课程id获取评论
    List<CommentsVO> selectByCourseId(Integer courseId);

    //根据课程id获取平均评分
    Double selectAVGStarsByCourseId(Integer courseId);

    //根据课程id获取总评价数量
    Integer selectNumByCourseId(Integer courseId);
}
