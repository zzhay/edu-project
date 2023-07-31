package com.ikun.eduproject.utils;

import java.util.Random;

/**
 * @Author zzhay
 * @Date 2023/7/31/031
 */
//生成验证码
public class GenerateCaptchaUtil {
    public static String generateCaptcha() {
        // 定义随机数的字符源
        String source = "0123456789";
        // 创建一个随机数生成器
        Random random = new Random();
        // 用于存储生成的随机数验证码
        StringBuilder code = new StringBuilder();

        // 循环生成指定长度的随机数验证码
        for (int i = 0; i < 6; i++) {
            // 从字符源中随机选择一个字符，并将其加入到验证码中
            int index = random.nextInt(source.length());
            code.append(source.charAt(index));
        }

        // 将生成的随机数验证码转为字符串并返回
        return code.toString();
    }
}
