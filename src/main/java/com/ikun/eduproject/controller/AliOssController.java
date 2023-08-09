package com.ikun.eduproject.controller;

import com.ikun.eduproject.utils.AliOssUtils;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 */
@RestController
@RequestMapping("/alioss")
@CrossOrigin
@Api(value = "提供aliOSS文件上传的接口", tags = "aliOSS文件上传")
public class AliOssController {

    @Resource
    private AliOssUtils aliOSSUtils;

    /**
     * 文件上传
     * @param avatar 文件
     * @return ResultVO
     * @throws IOException 异常
     */
    @PostMapping("/upload")
    public ResultVO<String> upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        String url = aliOSSUtils.upload(avatar);
        if (url != null) {
            return new ResultVO<>(StatusVO.INSERT_OK, "上传成功", url);
        } else {
            return new ResultVO<>(StatusVO.INSERT_OK,"上传失败",null);
        }
    }
}
