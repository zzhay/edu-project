package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课程对象",description = "课程信息")
public class Course implements Serializable {
    @ApiModelProperty(dataType = "Integer",required = false,value = "课程id")
    private int courseId;

    @ApiModelProperty(dataType = "String",required = true,value = "课程名")
    private String name;

    @ApiModelProperty(dataType = "BigDecimal",required = true,value = "价格")
    private BigDecimal price;

    @ApiModelProperty(dataType = "String",required = true,value = "课程描述")
    private int description;

    @ApiModelProperty(dataType = "int",required = false,value = "状态（0：锁定，1：未锁定）")
    private int statu;

    @ApiModelProperty(dataType = "int",required = false,value = "是否审核（0：已审核，1未审核）")
    private int checked;

    @ApiModelProperty(dataType = "Integer",required = true,value = "教师id")
    private int userId;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "创建时间")
    private Timestamp creatTime;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;
}