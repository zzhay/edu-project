package com.ikun.eduproject.config;

import com.ikun.eduproject.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author zzhay
 * @Date 2023/8/5/005
 */
//todo
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public Interceptor interceptor() {
        return new Interceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor())
                //拦截的url
                .addPathPatterns("/user/**",
                        "/stuCourses/**",
                        "/course/**",
                        "/comments/**",
                        "/assignment/**",
                        "/alioss/**")
                //放行的url
                .excludePathPatterns("/user/login", "/user/regist", "/user/getCaptcha", "/user/checkCaptcha", "/user/getUser",
                        "/subject/getAll",
                        "/course/getCourse1", "/course/getByCategory", "/course/getByCategoryOrderByPrice", "/course/getBySubName", "/course/getBySubNameOrderByPrice", "/course/queryFuzzy", "/course/queryFuzzyOrderByPrice", "/course/queryRandomCourses", "/course/getPopularCourses", "/course/addSearchFrequency", "/course/",
                        "/comments/getComment", "/comments/getCourseRatingSummary");
    }
}
