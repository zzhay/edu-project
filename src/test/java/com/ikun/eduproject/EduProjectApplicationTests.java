package com.ikun.eduproject;

import com.ikun.eduproject.dao.*;
import com.ikun.eduproject.es.EsCourseRepository;
import com.ikun.eduproject.pojo.Assignments;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.RedisUtil;
import com.ikun.eduproject.utils.SensitivewordFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;
import java.util.Set;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseAuditDao courseAuditDao;
    @Autowired
    private StuCoursesDao stuCoursesDao;
    @Autowired
    private AssignmentsDao assignmentsDao;
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    AliOSSUtils aliOSSUtils;
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private EsCourseRepository esCourseRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;

    @Test
    void contextLoads() {
        System.out.println(subjectDao.selectSubCategoryBySubName("经济思想史"));
    }


}
