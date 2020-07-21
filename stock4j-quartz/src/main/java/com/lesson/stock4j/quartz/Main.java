package com.lesson.stock4j.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * 功能概述：启动类
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.7
 * <p>Date: 2020-07-14 17-24
 */
@MapperScan(basePackages = {"com.lesson.stock4j.quartz.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableAutoConfiguration
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
