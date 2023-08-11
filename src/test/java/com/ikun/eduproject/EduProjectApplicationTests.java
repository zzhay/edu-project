package com.ikun.eduproject;

import com.ikun.eduproject.dao.UserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class EduProjectApplicationTests {


    @Test
    public void test1() {
        Random random = new Random();
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
        System.out.println(random.nextInt(9000) + 1000);
    }

}
