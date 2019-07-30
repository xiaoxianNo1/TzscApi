package com.xiaoxian;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(value = "com.xiaoxian.mapper")
/*@EnableAutoConfiguration(exclude = {
        RedisAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        JmsAutoConfiguration.class,
        JtaAutoConfiguration.class,
        TransactionAutoConfiguration.class,
        WebSocketAutoConfiguration.class
})*/
//@ComponentScan(basePackages = {"com.xiaoxian.mapper"})
@SpringBootApplication
public class JykzApiApplication {

    public static void main(String[] args) {
                 SpringApplication.run(JykzApiApplication.class, args);
    }
}
