package com.bequre.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  分页查询需要这个插件配置，否则无法分页查询
 */
@Configuration
public class MybatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //1. 创建分页插件
        PaginationInnerInterceptor pi = new PaginationInnerInterceptor(DbType.MYSQL);
        pi.setMaxLimit(1000L);
        //2. 添加分页插件
        interceptor.addInnerInterceptor(pi);

        return interceptor;
    }


}