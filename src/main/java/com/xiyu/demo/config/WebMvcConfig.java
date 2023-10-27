package com.xiyu.demo.config;

import com.xiyu.demo.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Date: 2023-10-08
 * Time: 21:11
 *
 * @author 陈子豪
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${user.uploadImageDir}")
    private String userUploadImagePah;

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.js")
                .excludePathPatterns("/**/*.css")
                .excludePathPatterns("/**/*.png")
                .excludePathPatterns("/**/*.jpg")
                .excludePathPatterns("/**/*.jpeg")
                .excludePathPatterns("/**/*.woff")
                .excludePathPatterns("/**/*.ttf")
                .excludePathPatterns("/**/*.map")
                .excludePathPatterns("/**/*.ico")
                .excludePathPatterns("/")
                .excludePathPatterns("/views/index.html")
                .excludePathPatterns("/home")
                .excludePathPatterns("/views/login.html")
                .excludePathPatterns("/login")
                .excludePathPatterns("/views/register.html")
                .excludePathPatterns("/register")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/views/bookDetail.html")
                .excludePathPatterns("/bookDetail")
                .excludePathPatterns("/views/categoryDetail.html")
                .excludePathPatterns("/categoryDetail")
                .excludePathPatterns("/categoryDetailBooks")
                .excludePathPatterns("/views/searchResult.html")
                .excludePathPatterns("/searchBooks")
                .excludePathPatterns("/test");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 服务器的保护措施, 服务器不能对外部暴露真实的资源路径, 需要配置虚拟路径映射访问。
        registry.addResourceHandler("/userImage/**")
                .addResourceLocations("file:///" + userUploadImagePah);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
