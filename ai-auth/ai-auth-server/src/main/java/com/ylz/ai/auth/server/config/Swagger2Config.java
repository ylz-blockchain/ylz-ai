package com.ylz.ai.auth.server.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

/**
 * @Description swagger 配置类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:32
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {
    @Autowired
    private ServletContext servletContext;
    @Value("${swagger.zuul.host}")
    private String host;
    @Value("${swagger.zuul.prefix}")
    private String prefix;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return prefix;
                    }
                })
                // 基本信息
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ylz.ai"))
                .paths(PathSelectors.regex("^(?!auth).*$"))
                .build();
    }

    /**
     * @description api文档的详细信息函数,注意这里的注解引用的是哪个
     * @author haifeng.lv
     * @updateTime 2019/12/12 17:04
     * @return: springfox.documentation.service.ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // //大标题
                .title("Spring-Cloud 后台服务API接口文档")
                // 版本号
                .version("1.0")
                // 描述
                .description("后台API接口 (授权头默认 Basic Y2xvdWQ6Y2xvdWQ=)," +
                        "前端登录接口/oauth/token")
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

}
