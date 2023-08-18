package com.ikun.eduproject.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * 阿里OSS工具类
 */
@Component
public class AliOssUtils {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 实现上传文件到OSS
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        // 获取上传的文件的输入流
        InputStream inputStream = multipartFile.getInputStream();

        Random random = new Random();
        int i = random.nextInt(9000) + 1000;
        // 获取文件扩展名
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 将随机数和文件扩展名连接在一起
        String newFilename = i + "." + fileExtension;

        // 避免文件覆盖
        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")) + newFilename;
        // 初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //上传文件到 OSS
        PutObjectResult object = ossClient.putObject(bucketName, fileName, inputStream);

        //文件访问路径
        String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

        // 关闭ossClient
        ossClient.shutdown();
        // 把上传到oss的路径返回
        return url;
    }

    /**
     * 删除文件
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
