package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;

    @Test
    void contextLoads() {

    }

}
