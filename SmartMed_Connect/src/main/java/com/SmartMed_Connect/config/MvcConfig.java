package com.SmartMed_Connect.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * MvcConfig 类用于配置 Spring MVC 和错误页面映射
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer, ErrorPageRegistrar {

    /**
     * 注册视图控制器，用于直接将请求映射到视图而无需经过控制器
     *
     * @param registry 视图控制器注册表
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 注册错误页面视图控制器
        registry.addViewController("/400").setViewName("error/400");
        registry.addViewController("/401").setViewName("error/401");
        registry.addViewController("/404").setViewName("error/404");
        registry.addViewController("/500").setViewName("error/500");
        // 注册其他页面视图控制器
        registry.addViewController("/feedback.html").setViewName("feedback");
        registry.addViewController("/empty.html").setViewName("empty");
    }

    /**
     * 配置错误页面映射，将特定的 HTTP 状态码映射到对应的错误页面
     *
     * @param registry 错误页面注册表
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        // 定义错误页面映射
        ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
        ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
        // 将错误页面映射添加到注册表
        registry.addErrorPages(error400Page, error401Page, error404Page, error500Page);
    }

}
