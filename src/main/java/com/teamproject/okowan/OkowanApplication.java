package com.teamproject.okowan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 시간 자동 생성
public class OkowanApplication {
    public static void main(String[] args) {
        SpringApplication.run(OkowanApplication.class, args);
    }

}
