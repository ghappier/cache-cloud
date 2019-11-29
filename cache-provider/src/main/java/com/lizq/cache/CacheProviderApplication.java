package com.lizq.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 服务提供者
 */
@Slf4j
@EnableCaching
@DubboComponentScan(basePackages = "com.lizq.cache.provider")// service实现包
@SpringBootApplication
public class CacheProviderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CacheProviderApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("服务提供者启动完毕");
    }
}
