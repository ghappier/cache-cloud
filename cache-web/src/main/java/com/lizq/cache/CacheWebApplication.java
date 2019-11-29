package com.lizq.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 服务调用者
 */
@Slf4j
@SpringBootApplication
@EnableDubbo
public class CacheWebApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CacheWebApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("服务调用者启动完毕");
    }
}
