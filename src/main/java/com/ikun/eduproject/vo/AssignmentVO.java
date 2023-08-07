package com.ikun.eduproject.vo;

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
 * @Date 2023/8/4/004
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "作业返回对象", description = "作业")
public class AssignmentVO implements Serializable {
    @ApiModelProperty("作业id")
    private Integer assignmentId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("课程id")
    private Integer courseId;

    @ApiModelProperty("作业url")
    private String assignmentUrl;

    @ApiModelProperty("获得的kun分")
    private BigDecimal credit;

    @ApiModelProperty("提交时间")
    private Timestamp time;
}
