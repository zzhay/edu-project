package com.ikun.eduproject.es;

import com.ikun.eduproject.pojo.ElasticsearchCourse;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/31/031
 * 在ES中进行CRUD操作或自定义查询
 */
@Repository
public interface EsCourseRepository extends ElasticsearchRepository<ElasticsearchCourse, Integer> {
    /**
     * 按照课程名迷糊查询
     * @param name 课程名
     * @return List
     */
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"or\"}}}]}}")
    List<ElasticsearchCourse> findByNameFuzzy(String name);

    List<ElasticsearchCourse> findBySubName(String subName);


}
