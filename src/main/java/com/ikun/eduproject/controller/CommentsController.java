package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Comments;
import com.ikun.eduproject.service.CommentsService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/comments")
@CrossOrigin
@Api(value = "提供评论管理的接口", tags = "评论管理")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    @ApiOperation("写评论接口")
    @PostMapping("/addComment")
    public ResultVO addComment(@RequestBody @Valid Comments comments) {
        return commentsService.addComment(comments);
    }

    @ApiOperation("获取评论接口")
    @GetMapping("/getComment")
    public ResultVO getComment(@RequestParam("courseId") @NotNull Integer courseId) {
        return commentsService.getComment(courseId);
    }

    @ApiOperation("获取课程评价统计")
    @GetMapping("/getCourseRatingSummary")
    public ResultVO getCourseRatingSummary(@RequestParam("courseId") @NotNull Integer courseId) {
        return commentsService.getCourseRatingSummary(courseId);
    }
}
