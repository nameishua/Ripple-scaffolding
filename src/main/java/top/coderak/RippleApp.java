package top.coderak;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot启动类
 *
 * @author zyh
 * @date 2019/7/21 0021
 */
@SpringBootApplication
//单体注掉
//@EnableDiscoveryClient
@ComponentScan(basePackages = {"top.coderak"})
@MapperScan(basePackages = {"top.coderak.mapper", "top.coderak.core.base.mapper"})
public class RippleApp {
    public static void main(String[] args) {

        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(RippleApp.class, args);

        System.out.println("程序正在运行...!@#$%^&");
    }
}
