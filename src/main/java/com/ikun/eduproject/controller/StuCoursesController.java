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
@RequestMapping("/stuCourses")
@CrossOrigin
@Api(value = "提供学生购买课程管理的接口", tags = "学生购买课程管理")
public class StuCoursesController {
}
