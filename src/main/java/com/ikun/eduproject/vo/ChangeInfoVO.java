package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改用户信息对象", description = "修改用户信息")
public class ChangeInfoVO implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;
}
