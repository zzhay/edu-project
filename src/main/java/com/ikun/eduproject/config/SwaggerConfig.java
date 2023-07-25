package com.ikun.eduproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /* http://localhost:8080/swagger-ui.html
    * 第三方文档：http://localhost:8080/doc.html （更好用）
    * swagger会帮助我们生成接口文档
    * 1：配置生成的文档信息
    * 2: 配置生成规则*/
    /*Docket封装接口文档信息*/
    @Bean
    public Docket getDocket(){
        //创建封面信息对象
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title(" 智慧教育平台 后端接口说明");
        ApiInfo apiInfo =  apiInfoBuilder.build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo) //指定生成的文档中的封面信息：文档标题、版本、作者
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ikun.eduproject.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
