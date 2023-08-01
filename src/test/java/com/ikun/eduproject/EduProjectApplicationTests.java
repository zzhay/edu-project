package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.es.EsCourseRepository;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.ElasticsearchCourse;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.RedisUtil;
import com.ikun.eduproject.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;

    @Autowired
    AliOSSUtils aliOSSUtils;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private EsCourseRepository esCourseRepository;

    @Test
    void contextLoads() {
        List<Course> courses = courseDao.selectBySubName("数学");
        for (Course cours : courses) {

            System.out.println(cours);
        }
    }

}
