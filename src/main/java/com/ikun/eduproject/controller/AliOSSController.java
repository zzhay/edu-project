package com.ikun.eduproject.controller;

import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(value = "提供aliOSS文件上传的接口", tags = "aliOSS文件上传")
public class AliOSSController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    /**
     * 文件上传
     * @param avatar
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public ResultVO upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        String url = aliOSSUtils.upload(avatar);
        return new ResultVO(0,null,url);
    }
}
