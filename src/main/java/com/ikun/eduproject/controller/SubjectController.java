package com.ikun.eduproject.controller;

import com.ikun.eduproject.service.SubjectService;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 */

@RestController
@RequestMapping("/subject")
@CrossOrigin
@Api(value = "提供学科分类管理的接口", tags = "学科分类管理")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/getAll")
    public ResultVO getAll() {
        ResultVO result = subjectService.getAll();
        return result;
    }
}
