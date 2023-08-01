package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
public class StudentCourse implements Serializable {
    @ApiModelProperty(dataType = "Integer",required = false,value = "学生课程id")
    private Integer stuCourseId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "课程id")
    @NotNull(message = "课程id不能为空")
    private Integer courseId;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "购买时间")
    private Timestamp time;
}
