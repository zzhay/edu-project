package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Comments;
import com.ikun.eduproject.service.CommentsService;
import com.ikun.eduproject.vo.CommentsVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/comments")
@CrossOrigin
@Api(value = "提供评论管理的接口", tags = "评论管理")
public class CommentsController {
    @Resource
    private CommentsService commentsService;

    /**
     * 写评论接口
     *
     * @param comments 评论对象
     * @return ResultVO
     */
    @ApiOperation("写评论接口")
    @PostMapping("/addComment")
    public ResultVO<Set<String>> addComment(@RequestBody @Valid Comments comments) {
        return commentsService.addComment(comments);
    }

    /**
     * 删除评论接口
     *
     * @param commentId 评论id
     * @return ResultVO
     */
    @ApiOperation("删除评论接口")
    @PostMapping("/delComment")
    public ResultVO<String> delComment(@RequestParam("commentId") @NotNull Integer commentId) {
        return commentsService.delComment(commentId);
    }

    /**
     * 更新评论接口
     *
     * @param comments 评论对象
     * @return ResultVO
     */
    @ApiOperation("更新评论接口")
    @PostMapping("/changeComment")
    public ResultVO<Set<String>> changeComment(@RequestBody @Valid Comments comments) {
        return commentsService.changeComment(comments);
    }

    /**
     * 获取评论接口
     *
     * @param courseId 评论id
     * @return ResultVO
     */
    @ApiOperation("获取评论接口")
    @GetMapping("/getComment")
    public ResultVO<List<CommentsVO>> getComment(@RequestParam("courseId") @NotNull Integer courseId) {
        return commentsService.getComment(courseId);
    }

    /**
     * 获取课程评价统计
     *
     * @param courseId 评论id
     * @return ResultVO
     */
    @ApiOperation("获取课程评价统计")
    @GetMapping("/getCourseRatingSummary")
    public ResultVO<Map<String, String>> getCourseRatingSummary(@RequestParam("courseId") @NotNull Integer courseId) {
        return commentsService.getCourseRatingSummary(courseId);
    }
}
