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
@ApiModel(value = "用户对象",description = "用户信息")
public class User implements Serializable {

    @ApiModelProperty(dataType = "Integer",required = false,value = "用户id")
    private Integer userId;

    @ApiModelProperty(dataType = "String",required = true,value = "用户名")
    private String username;

    @ApiModelProperty(dataType = "String",required = true,value = "密码")
    private String password;

    @ApiModelProperty(dataType = "int",required = true,value = "角色（（0：管理员，1：老师，2：学生））")
    private Integer role;

    @ApiModelProperty(dataType = "String",required = true,value = "头像url")
    private String imageUrl;

    @ApiModelProperty(dataType = "String",required = true,value = "电话")
    private String phone;

    @ApiModelProperty(dataType = "String",required = true,value = "邮件")
    private String email;

    @ApiModelProperty(dataType = "BigDecimal", required = true, value = "kun分")
    private BigDecimal credit;

    @ApiModelProperty(dataType = "Integer",required = false,value = "状态（0：锁定，1：未锁定，2：教师身份审核中，3：审核未通过）")
    private Integer statu;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "创建时间")
    private Timestamp creatTime;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;

}
