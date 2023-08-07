package com.ikun.eduproject.interceptor;

import com.alibaba.fastjson.JSON;
import com.ikun.eduproject.pojo.User;
import com.ikun.eduproject.utils.RedisUtil;
import com.ikun.eduproject.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author zzhay
 * @Date 2023/8/5/005
 * 拦截器
 */
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        //获取token
        String token = request.getHeader("token");
        //检测token是否为空
        if (StringUtils.isEmpty(token)) {
            response.getWriter().write(this.errorResponse("未登录"));
            return false;
        }
        //检查token是否过期
        if (TokenUtil.isTokenExpired(token)) {
            response.getWriter().write(this.errorResponse("登录过期，请重新登录"));
            return false;
        }
        //检测登录是否过期
        try {
            User user = (User) redisUtil.getCacheObject(token);
            if (Objects.isNull(user)) {
                response.getWriter().write(this.errorResponse("登录过期，请重新登录"));
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.getWriter().write(this.errorResponse("发生异常"));
            return false;
        }
        return true;
    }

    private String errorResponse(String msg) {
        Map map = new HashMap(2) {
            {
                put("code", -1);
                put("msg", msg);
            }
        };
        return JSON.toJSONString(map);
    }
}
