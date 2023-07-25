package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/7/25/025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> implements Serializable {

    @ApiModelProperty("返回码")
    private int code;
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("返回对象")
    private T data;
}
