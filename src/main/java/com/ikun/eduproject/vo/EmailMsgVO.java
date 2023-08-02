package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;

/**
 * @Author zzhay
 * @Date 2023/7/29/029
 */
@ApiModel(value = "邮件信息", description = "邮件信息")
public class EmailMsgVO {
    public static String REGIST = "智学云注册提示";
    public static String ACCOUNT = "智学云账号变动提示";
    public static String COURSE = "智学云课程审核提示";


    public static String registStuMsg(String username) {
        return "【智学云】你的账号 " + username + " 已注册成功";
    }

    public static String registTeaMsg(String username) {
        return "【智学云】你的教师账号 " + username + " 正在审核，请注意接受后续邮件";
    }

    public static String registNoTeaMsg(String username) {
        return "【智学云】你的教师账号 " + username + " 审核未通过，请重新注册";
    }

    public static String registOKTeaMsg(String username) {
        return "【智学云】你的教师账号 " + username + " 审核已通过。";
    }

    public static String accountMsg0(String username) {
        return "【智学云】你的账号 " + username + " 已被冻结，请联系管理员了解详情";
    }

    public static String accountMsg1(String username) {
        return "【智学云】你的账号 " + username + " 已恢复正常";
    }

    public static String accountMsg2(String username) {
        return "【智学云】你的账号 " + username + " 密码已修改";
    }

    public static String accountMsg3(String captcha) {
        return "【智学云】验证码：" + captcha + "（5分钟类有效，如非本人操作，请忽略）";
    }

    public static String coursePassed(String name) {
        return "【智学云】你的课程：" + name + "审核已通过";
    }

    public static String courseNotPassed(String name, String reason) {
        return "【智学云】你的课程：" + name + "审核未通过,原因如下：" + reason;
    }

}
