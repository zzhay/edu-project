package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(dataType = "Integer",required = false,value = "课程id")
    private Integer courseId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "教师id")
    private Integer userId;

    @ApiModelProperty(dataType = "String",required = true,value = "课程名称")
    @NotNull(message = "课程名不能为空")
    private String name;

    @ApiModelProperty(dataType = "String",required = true,value = "作者")
    @NotNull(message = "作者不能为空")
    private String author;

    @ApiModelProperty(dataType = "BigDecimal",required = true,value = "价格")
    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(dataType = "String",required = true,value = "课程描述")
    @NotNull(message = "描述不能为空")
    private String description;

    @ApiModelProperty(dataType = "String",required = true,value = "学科名")
    @NotNull(message = "学科名不能为空")
    private String subName;

    @ApiModelProperty(dataType = "String",required = true,value = "头像url")
    private String imageUrl;

    @ApiModelProperty(dataType = "String",required = true,value = "附件url")
    private String contentUrl;

    @ApiModelProperty(dataType = "Integer",required = false,value = "状态（0：下架，1：上架，2：未上架）")
    private Integer statu;

    @ApiModelProperty(dataType = "Integer",required = false,value = "是否审核（0：待审核，1：审核通过，2：未通过）")
    private Integer checked;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;
}
