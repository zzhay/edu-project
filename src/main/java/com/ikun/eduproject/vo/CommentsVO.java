package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/8/3/003
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "获取的评论信息")
public class CommentsVO implements Serializable {
    @ApiModelProperty("头像url")
    @NotNull
    private Integer imageUrl;

    @ApiModelProperty("用户名")
    @NotNull
    private Integer username;

    @ApiModelProperty( "评论内容")
    @NotNull
    private String text;

    @ApiModelProperty("打分星级（0~5）")
    @NotNull
    private Double stars;

    @ApiModelProperty("评论时间")
    private Timestamp time;
}
