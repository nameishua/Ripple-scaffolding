package top.coderak;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Ripple application entry point.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"top.coderak"})
@MapperScan(basePackages = {"top.coderak.mapper", "top.coderak.core.base.mapper"})
public class RippleApp {

    public static void main(String[] args) {
        SpringApplication.run(RippleApp.class, args);
    }
}
