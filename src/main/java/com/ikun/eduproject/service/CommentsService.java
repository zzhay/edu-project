package com.ikun.eduproject.service;

import com.ikun.eduproject.pojo.Comments;
import com.ikun.eduproject.vo.CommentsVO;
import com.ikun.eduproject.vo.ResultVO;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * UserService提供了用课程评论相关功能的接口。
 * 定义了评论管理等相关功能的抽象方法。
 */
public interface CommentsService {

    /**
     * 写评论
     * @param comments 评论信息
     * @return ResultVO
     */
    ResultVO<Set> addComment(Comments comments);

    /**
     * 更新评论
     * @param comments 评论信息
     * @return ResultVO
     */
    ResultVO<String> changeComment(Comments comments);

    /**
     * 删除评论
     * @param commentId 评论id
     * @return ResultVO
     */
    ResultVO<String> delComment(Integer commentId);

    /**
     * 获取评论
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<List<CommentsVO>> getComment(Integer courseId);

    /**
     * 获取评价统计
     * @param courseId 课程id
     * @return ResultVO
     */
    ResultVO<Map<String, Integer>> getCourseRatingSummary(Integer courseId);
}
