package com.ikun.eduproject.es;

import com.ikun.eduproject.pojo.ElasticsearchCourse;
import org.springframework.data.domain.Sort;
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
     *
     * @param name 课程名
     * @return List
     */
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"or\"}}}]}}")
    List<ElasticsearchCourse> findByNameFuzzy(String name);

    /**
     * 根据课程名查询并按价格升序
     *
     * @param name 课程名
     * @return List
     */
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"or\"}}}]}, \"sort\": [{\"price\": {\"order\": \"asc\"}}]}")
    List<ElasticsearchCourse> findByNameFuzzyOrderByPriceAsc(String name);

    /**
     * 根据课程名查询并按价格降序
     *
     * @param name 课程名
     * @return List
     */
    @Query("{\"bool\": {\"should\": [{\"match\": {\"name\": {\"query\": \"?0\", \"operator\": \"or\"}}}]}, \"sort\": [{\"price\": {\"order\": \"desc\"}}]}")
    List<ElasticsearchCourse> findByNameFuzzyOrderByPriceDesc(String name);

    /**
     * 按照学科名查询
     *
     * @param subName 学科名
     * @return List
     */
    List<ElasticsearchCourse> findBySubName(String subName);

    /**
     * 按照学科名查询并按价格升序
     *
     * @param subName 学科名
     * @return List
     */
    List<ElasticsearchCourse> findBySubNameOrderByPriceAsc(String subName);

    /**
     * 按照学科名查询并按价格降序
     *
     * @param subName 学科名
     * @return List
     */
    List<ElasticsearchCourse> findBySubNameOrderByPriceDesc(String subName);

}
