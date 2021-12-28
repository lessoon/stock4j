package com.lesson.stock4j.analysis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 功能概述：启动类
 *
 * @author Du Lee
 * @version 1.0
 * @since JDK 1.7
 * <p>Date: 2020-07-14 17-24
 */
@MapperScan(basePackages = {"com.lesson.stock4j.*"})
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
