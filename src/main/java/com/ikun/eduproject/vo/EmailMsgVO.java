package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;

/**
 * @Author zzhay
 * @Date 2023/7/29/029
 */
@ApiModel(value = "邮件信息",description = "邮件信息")
public class EmailMsgVO {
    public static String regSub = "智学云注册信息";
    public static String statuSub = "智学云账号变动信息";


    public static String registStuMsg(String username) {
        return  "您好，你的账号 "+username+" 已注册成功！";
    }

    public static String registTeaMsg(String username) {
        return "您好，你的教师账号 "+username+" 正在审核，请注意接受后续邮件。";
    }

    public static String registNoTeaMsg(String username) {
        return "您好，你的教师账号 "+username+" 审核未通过，请重新注册。";
    }

    public static String statuMsg0(String username) {
        return "您好，你的账号 "+username+" 已被冻结，请联系管理员了解详情。";
    }

    public static String statuMsg1(String username) {
        return "您好，你的账号 "+username+" 已恢复正常。";
    }
}
