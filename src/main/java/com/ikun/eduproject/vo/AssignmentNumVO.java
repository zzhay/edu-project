package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author zzhay
 * @Date 2023/8/4/004
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "待批改作业数量返回对象")
public class AssignmentNumVO implements Serializable {
    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("课程名")
    private String name;

    @ApiModelProperty("数量")
    private Integer num;
}
