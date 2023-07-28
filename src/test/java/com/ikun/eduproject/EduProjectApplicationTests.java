package com.ikun.eduproject;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.vo.GetCourseChecked;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    AliOSSUtils aliOSSUtils;
    @Test
    void contextLoads() {
        String imageUrl = "https://ikun-edu.oss-cn-beijing.aliyuncs.com/2023-07-28-17-53-39tx1.png";
        aliOSSUtils.deleteImageByUrl(imageUrl);

    }

}
