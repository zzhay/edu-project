package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.CommentsDao;
import com.ikun.eduproject.dao.StuCoursesDao;
import com.ikun.eduproject.pojo.Comments;
import com.ikun.eduproject.service.CommentsService;
import com.ikun.eduproject.utils.SensitivewordFilter;
import com.ikun.eduproject.vo.CommentsVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * CommentsServiceImpl是课程评论相关功能的Service实现类。
 * 提供了评论管理相关功能的具体实现。
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Resource
    private CommentsDao commentsDao;
    @Resource
    private StuCoursesDao stuCoursesDao;

    /**
     * 写评论
     * @param comments 评论信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<Set<String>> addComment(Comments comments) {
        //权限校验，是否购买该课程
        Integer i = stuCoursesDao.selectByUidAndCid(comments.getUserId(), comments.getCourseId());
        if (i == null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "评论失败,你还未购买该课程，无法评论", null);
        }
        //判断是否评论过
        if (commentsDao.selectByUidAndCid(comments.getUserId(), comments.getCourseId()) != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "你已经评论过该课程，无法重复评论", null);
        }
        //敏感词判断
        SensitivewordFilter filter = new SensitivewordFilter();
        Set<String> set = filter.getSensitiveWord(comments.getText(), 2);
        if (set.size() != 0) {
            return new ResultVO<>(StatusVO.INSERT_NO, "评论中包含敏感词", set);
        }

        if (commentsDao.insertComment(comments) > 0) {
            return new ResultVO<>(StatusVO.INSERT_OK, "评论成功", null);
        } else {
            return new ResultVO<>(StatusVO.INSERT_NO, "评论失败", null);
        }
    }

    /**
     * 更新评论
     * @param comments 评论信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> changeComment(Comments comments) {
        int i = commentsDao.updateCommentById(comments);
        if (i > 0) {
            return new ResultVO<>(StatusVO.INSERT_OK, "更新成功", null);
        } else {
            return new ResultVO<>(StatusVO.INSERT_NO, "更新失败", null);
        }
    }

    /**
     * 删除评论
     * @param commentId 评论id
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> delComment(Integer commentId) {
        int i = commentsDao.deleteCommentByid(commentId);
        if (i > 0) {
            return new ResultVO<>(StatusVO.INSERT_OK, "删除成功", null);
        } else {
            return new ResultVO<>(StatusVO.INSERT_NO, "删除失败", null);
        }
    }

    /**
     * 获取评论
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<CommentsVO>> getComment(Integer courseId) {
        List<CommentsVO> list = commentsDao.selectByCourseId(courseId);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", list);
    }

    /**
     * 获取评价统计
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<Map<String, String>> getCourseRatingSummary(Integer courseId) {
        Double avgStars = commentsDao.selectAVGStarsByCourseId(courseId);
        if (avgStars == null) {
            return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", new HashMap<String, String>(2) {
                {
                    put("avgStars", "0");
                    put("num", "0");
                }
            });
        }
        //格式化，只保留一位小数
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String format = decimalFormat.format((double)avgStars);
        //总评论数
        String num = String.valueOf(commentsDao.selectNumByCourseId(courseId));
        Map<String, String> map = new HashMap<>(2);
        map.put("avgStars", format);
        map.put("num", num);
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", map);
    }

}
