package com.ikun.eduproject.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@Component
public class AliOSSUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 实现上传图片到OSS
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = multipartFile.getInputStream();

        // 避免文件覆盖
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + multipartFile.getOriginalFilename();
        // 初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //上传文件到 OSS
        ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

        // 关闭ossClient
        ossClient.shutdown();
        // 把上传到oss的路径返回
        return url;
    }

    /**
     * 实现上传图片到OSS
     */
    public boolean deleteImageByUrl(String imageUrl) {
        String objectKey = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
        // 初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除图片文件
        try {
            ossClient.deleteObject(bucketName, objectKey);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            // 关闭OSS客户端
            ossClient.shutdown();
        }
    }


}
