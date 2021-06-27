package org.lantern.thrift.server;

import org.lantern.thrift.support.ThriftApplicationListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
       // SpringApplication.run(App.class, args).addApplicationListener(new ThriftApplicationListener());


       new SpringApplicationBuilder().listeners(new ThriftApplicationListener()).sources(App.class).run(args);


       // new SpringApplicationBuilder().sources(App.class).run(args);
    }

}
