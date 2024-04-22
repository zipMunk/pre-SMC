package com.SmartMed_Connect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.SmartMed_Connect.dao")
public class SmartMedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartMedicineApplication.class, args);
    }

}
