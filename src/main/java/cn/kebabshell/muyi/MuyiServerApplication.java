package cn.kebabshell.muyi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn/kebabshell/muyi/common/mapper")
public class MuyiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuyiServerApplication.class, args);
    }

}
