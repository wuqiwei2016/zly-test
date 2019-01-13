package com.zpkj.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/zpkj/exam/dao")
@EnableAutoConfiguration
public class GdExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdExamApplication.class, args);
	}
}
