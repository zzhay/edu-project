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
 * @Date 2023/7/28/028
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "学科对象",description = "学科分类信息")
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(dataType = "Integer",required = false,value = "学科id")
    private Integer subId;

    @ApiModelProperty(dataType = "String",required = false,value = "学科名")
    private String subName;

    @ApiModelProperty(dataType = "String",required = false,value = "学科类别")
    private String subCategory;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "创建时间")
    private Timestamp createTime;

}
