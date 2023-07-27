package com.ikun.eduproject.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/comments")
@CrossOrigin
@Api(value = "提供评论管理的接口", tags = "评论管理")
public class CommentsController {
}
