package com.ctmill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ctmill.*.mapper")
public class CottonMillApplication {

    public static void main(String[] args) {
        SpringApplication.run(CottonMillApplication.class, args);
    }

}
