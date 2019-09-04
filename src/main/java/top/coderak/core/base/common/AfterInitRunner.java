package top.coderak.core.base.common;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AfterInitRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.println("AfterInitRunner");
    }

}
