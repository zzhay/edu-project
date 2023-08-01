package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/8/1/001
 * 登录信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录信息对象", description = "登录信息")
public class LoginVO implements Serializable {
    @ApiModelProperty("用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    private String password;
}
