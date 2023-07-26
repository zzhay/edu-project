package com.ikun.eduproject;

import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println(userDao.selectByPhone("123"));
    }

}
