package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    AliOSSUtils aliOSSUtils;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
       emailUtil.sendMessage("15415@qq.com","标题","内容");

    }

}
