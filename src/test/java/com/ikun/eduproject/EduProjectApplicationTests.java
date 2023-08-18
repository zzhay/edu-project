package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseAuditDao;
import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.CourseAudit;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.utils.SensitivewordFilter;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Set;


@SpringBootTest
public class EduProjectApplicationTests {


    @Test
    public void test1() {
        SensitivewordFilter filter = new SensitivewordFilter();
        Set<String> set = filter.getSensitiveWord("人民报", 2);
        System.out.println(set);
    }

}
