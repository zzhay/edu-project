package com.ikun.eduproject.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @Author zzhay
 * @Date 2023/8/14/014
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课程课时信息",description = "课程课时信息")
public class Periods {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(dataType = "Integer",required = true,value = "课程课时id")
    private Integer periodId;

    @ApiModelProperty(dataType = "Integer",required = false,value = "课程id")
    @NotNull(message = "课程id不能为空")
    private Integer courseId;

    @ApiModelProperty(dataType = "Integer",required = true,value = "第几课时")
    @NotNull(message = "课时不能为空")
    private Integer periods;

    @ApiModelProperty(dataType = "String",required = true,value = "附件url")
    private String contentUrl;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(dataType = "Timestamp",required = false,value = "更新时间")
    private Timestamp updateTime;
}
