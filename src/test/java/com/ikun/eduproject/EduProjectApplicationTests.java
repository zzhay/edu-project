package com.ikun.eduproject;

import com.ikun.eduproject.dao.CourseAuditDao;
import com.ikun.eduproject.dao.CourseDao;
import com.ikun.eduproject.dao.UserDao;
import com.ikun.eduproject.pojo.CourseAudit;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class EduProjectApplicationTests {

    @Autowired
    private CourseAuditDao courseAuditDao;

    @Test
    public void test1() {
        List<GetCourseCheckedVO> li = courseAuditDao.selectAllCheck();
        System.out.println(li);
    }

}
