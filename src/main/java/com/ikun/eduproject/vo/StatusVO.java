package com.ikun.eduproject.vo;

import io.swagger.annotations.ApiModel;

/**
 * @Author zzhay
 * 状态码信息
 */
@ApiModel(value = "状态码信息",description = "状态码信息")
public class StatusVO {

    //注册状态码
    public static final int REGIST_OK = 2001;
    public static final int REGIST_NO = 4001;

    //登录状态码
    public static final int LOGIN_OK = 2002;
    public static final int LOGIN_NO = 4002;
    //账号被锁定
    public static final int LOGIN_NO_STATU = 4022;

    //更新状态码
    public static final int UPDATE_OK = 2003;
    public static final int UPDATE_NO = 4003;

    //查询状态码
    public static final int SELECT_OK = 2004;
    public static final int SELECT_NO = 4004;

    //新增状态码
    public static final int INSERT_OK = 2005;
    public static final int INSERT_NO = 4005;

    //邮件状态码
    public static final int EMALI_OK = 2006;
    public static final int EMALI_NO = 4006;

    //验证码状态码
    public static final int CAPTCHA_OK = 2007;
    public static final int CAPTCHA_NO = 4007;
}
