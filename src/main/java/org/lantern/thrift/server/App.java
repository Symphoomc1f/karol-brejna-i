package org.lantern.thrift.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "org.lantern.thrift.server")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
