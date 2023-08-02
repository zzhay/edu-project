package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseAuditDao;
import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.es.EsCourseRepository;
import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.ElasticsearchCourse;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.utils.AliOSSUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.utils.RedisUtil;
import com.ikun.eduproject.vo.ResultVO;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class EduProjectApplicationTests {

    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseAuditDao courseAuditDao;

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
        System.out.println(esCourseRepository.findBySubName("fas"));

    }


}
