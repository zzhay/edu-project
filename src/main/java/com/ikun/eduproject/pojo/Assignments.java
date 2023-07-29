package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/7/29/029
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作业对象",description = "作业信息")
public class Assignments implements Serializable {
    @ApiModelProperty(dataType = "Integer",required = false,value = "作业id")
    private Integer assignmentId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "学生id")
    private Integer userId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "课程id")
    private Integer courseId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "作业url")
    private String assignmentUrl;
}
