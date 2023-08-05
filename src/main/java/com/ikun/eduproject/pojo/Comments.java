package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/7/26/026
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "评论对象",description = "评论信息")
public class Comments implements Serializable {
    @ApiModelProperty(dataType = "Integer",required = false,value = "评论id")
    private Integer commentsId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "用户id")
    private Integer userId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "课程id")
    private Integer courseId;

    @ApiModelProperty(dataType = "String",required = true,value = "评论内容")
    @NotNull
    private String text;

    @ApiModelProperty(dataType = "Integer",required = true,value = "打分星级（0~5）")
    @NotNull
    private Integer stars;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "评论时间")
    private Timestamp time;
}
