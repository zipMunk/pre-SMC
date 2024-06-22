package com.SmartMed_Connect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//标记这是一个 Spring Boot 应用程序，启用自动配置和组件扫描。
@MapperScan("com.SmartMed_Connect.dao")//扫描指定包路径下的 MyBatis Mapper 接口，并自动创建代理实现类。
public class SmartMedicineApplication {

    public static void main(String[] args) {
        //启动 Spring Boot 应用程序，传递主配置类和命令行参数。
        SpringApplication.run(SmartMedicineApplication.class, args);
    }
}
