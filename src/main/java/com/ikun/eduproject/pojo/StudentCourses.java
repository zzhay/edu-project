package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "学生拥有课程对象",description = "学生拥有课程信息")
public class StudentCourses implements Serializable {
    @ApiModelProperty(dataType = "Integer",required = false,value = "用户id")
    private int userId;

    @ApiModelProperty(dataType = "Integer",required = false,value = "课程id")
    private int courseId;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "购买时间")
    private Timestamp time;
}