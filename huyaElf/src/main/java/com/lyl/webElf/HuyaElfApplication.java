package com.lyl.webElf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lyl.webElf.utils.SpringUtil;


@SpringBootApplication
@MapperScan("com.lyl.webElf.dao")
public class HuyaElfApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuyaElfApplication.class, args);
		//System.out.println(SpringUtil.getBean("digBeanController"));
	}
}
