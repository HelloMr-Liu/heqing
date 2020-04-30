package com.huijianzhu.heqing;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
/**
 * ================================================================
 * 说明：合庆项目启动类入口
 * <p>
 * 作者          时间                    注释
 * 刘梓江    2020/4/30  10:01            创建
 * =================================================================
 **/
@SpringBootApplication
@MapperScan({"com.huijianzhu.heqing.mapper"})
public class HeQingMain {
    public static void main(String[] args) {
        SpringApplication.run(HeQingMain.class);
    }
    @Bean
    //由于 @PropertySource 不支持yml文件的对象转换 默认支持properties
    //所以手动配置自定义 yml文件
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("applicationdatabaseconfig.yml"));
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}
