package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "待审核课程返回对象",description = "待审核课程")
public class GetCourseChecked {

    @ApiModelProperty(dataType = "Integer",required = false,value = "课程id")
    private Integer courseId;

    @ApiModelProperty(dataType = "String",required = true,value = "用户名")
    private String username;

    @ApiModelProperty(dataType = "String",required = true,value = "课程名")
    private String name;

    @ApiModelProperty(dataType = "String",required = true,value = "作者")
    private String author;

    @ApiModelProperty(dataType = "BigDecimal",required = true,value = "价格")
    private BigDecimal price;

    @ApiModelProperty(dataType = "String",required = true,value = "课程描述")
    private String description;

    @ApiModelProperty(dataType = "String",required = true,value = "头像url")
    private String imageUrl;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;
}