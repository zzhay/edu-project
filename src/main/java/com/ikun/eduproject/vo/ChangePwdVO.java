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
@ApiModel(value = "修改密码对象", description = "修改密码息")
public class ChangePwdVO implements Serializable {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("旧密码")
    private String oldPwd;

    @ApiModelProperty("新密码")
    private String newPwd;
}
