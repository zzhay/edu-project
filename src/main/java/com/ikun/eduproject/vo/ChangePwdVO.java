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
 * @Date 2023/7/27/027
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "修改密码对象", description = "修改密码")
public class ChangePwdVO implements Serializable {

    @ApiModelProperty(dataType = "String",required = true,value = "邮箱")
    @NotNull(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty("新密码")
    @NotNull(message = "新密码不能为空")
    private String newPwd;
}
