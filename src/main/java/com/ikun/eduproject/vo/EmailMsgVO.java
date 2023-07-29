package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;

/**
 * @Author zzhay
 * @Date 2023/7/29/029
 */
@ApiModel(value = "邮件信息",description = "邮件信息")
public class EmailMsgVO {
    public static String regSub = "智学云注册信息";

    public static String registStuMsg(String username) {
        return  "您好，你的账号 "+username+" 已注册成功！";
    }

    public static String registTeaMsg(String username) {
        return "您好，你的教师账号 "+username+" 正在审核，请注意接受后续邮件。";
    }
}
