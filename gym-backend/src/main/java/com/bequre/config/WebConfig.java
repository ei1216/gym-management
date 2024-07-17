package com.bequre.config;

import com.bequre.interceptors.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;

    /**
     * 添加拦截器到拦截器注册表中。
     *
     * 此方法用于配置Spring MVC的拦截器，以便在请求处理之前或之后执行自定义逻辑。
     * 通过调用registry的addInterceptor方法，将请求拦截器添加到注册表中。
     * 接着，使用excludePathPatterns方法指定了不需要被拦截的URL路径，这里是管理员登录和注册页面。
     * 这样配置的目的是为了让这些特定的URL路径可以直接访问，而不需要经过请求拦截器的处理。
     *
     * @param registry 拦截器注册表，用于管理应用中的拦截器。
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor)
                .excludePathPatterns("/admin/login", "/admin/register");
    }
}
