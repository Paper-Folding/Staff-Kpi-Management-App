package ndky.paper.kpimgrapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("ndky.paper.kpimgrapp.Mappers")
@SpringBootApplication
public class StaffKpiManagementAppBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffKpiManagementAppBackApplication.class, args);
    }

}
