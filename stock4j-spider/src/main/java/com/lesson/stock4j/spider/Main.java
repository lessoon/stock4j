package com.lesson.stock4j.spider;

import com.lesson.stock4j.common.util.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

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
@Import(SpringUtils.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

}
