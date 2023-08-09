package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author zzhay
 * @Date 2023/7/31/031
 */
@Data
@Document(indexName = "course_index")
@ApiModel(value = "ES课程对象",description = "ES课程信息")
public class ElasticsearchCourse{
    @Id
    private Integer courseId;
    private Integer userId;
    @Field(type = FieldType.Text, store = true)
    private String name;
    private String author;
    private BigDecimal price;
    private String description;
    private String subName;
    private String subCategory;
    private String imageUrl;
    private String contentUrl;
    private Integer statu;
    private Integer checked;
    private Date creatTime;
    private Date updateTime;
    private Integer searchFrequency;

    //将原始的Course类对象转换为ElasticsearchCourse对象
    public static ElasticsearchCourse fromCourse(Course course) {
        ElasticsearchCourse esCourse = new ElasticsearchCourse();
        esCourse.setCourseId(course.getCourseId());
        esCourse.setUserId(course.getUserId());
        esCourse.setName(course.getName());
        esCourse.setAuthor(course.getAuthor());
        esCourse.setPrice(course.getPrice());
        esCourse.setDescription(course.getDescription());
        esCourse.setSubName(course.getSubName());
        esCourse.setImageUrl(course.getImageUrl());
        esCourse.setContentUrl(course.getContentUrl());
        esCourse.setStatu(course.getStatu());
        esCourse.setChecked(course.getChecked());
        esCourse.setCreatTime(course.getCreateTime());
        esCourse.setUpdateTime(course.getUpdateTime());
        return esCourse;
    }
}
