package com.lyl.webElf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.lyl.webElf.utils.SpringUtil;

@SpringBootApplication
@MapperScan("com.lyl.webElf.mapper")
public class HuyaElfApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuyaElfApplication.class, args);
		//System.out.println(SpringUtil.getBean("digBeanController"));
	}
}
