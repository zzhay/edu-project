package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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

    @ApiModelProperty(dataType = "String",required = true,value = "作业url")
    private String assignmentUrl;

    @ApiModelProperty(dataType = "BigDecimal", required = false, value = "获得的kun分")
    private BigDecimal credit;

    @ApiModelProperty(dataType = "Integer",required = false,value = "状态（0：未批改，1：已批改）")
    private Integer statu;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "提交时间")
    private Timestamp time;
}
