package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/7/31/031
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "忘记密码对象", description = "忘记密码")
public class ForgetPwdVO implements Serializable {
    @ApiModelProperty(dataType = "String",required = true,value = "用户名")
    private String username;

    @ApiModelProperty(dataType = "String",required = true,value = "邮箱")
    private String email;

    @ApiModelProperty(dataType = "String",required = true,value = "密码")
    private String password;
}
