package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "待审核课程返回对象",description = "待审核课程")
public class GetCourseCheckedVO implements Serializable {

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

    @ApiModelProperty(dataType = "String", required = true, value = "课程描述")
    private String description;

    @ApiModelProperty(dataType = "String",required = true,value = "学科名")
    @NotNull(message = "学科名不能为空")
    private String subName;

    @ApiModelProperty(dataType = "Integer",required = true,value = "总课时")
    //@NotNull(message = "总课时不能为空")
    private Integer periodAll;

    @ApiModelProperty(dataType = "Integer",required = true,value = "第几课时")
    //@NotNull(message = "课时不能为空")
    private Integer periods;

    @ApiModelProperty(dataType = "String",required = true,value = "头像url")
    private String imageUrl;

    @ApiModelProperty(dataType = "String",required = true,value = "课程内容url")
    private String contentUrl;

    @ApiModelProperty(dataType = "Date",required = true,value = "结束时间")
    private Date endTime;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;
}
