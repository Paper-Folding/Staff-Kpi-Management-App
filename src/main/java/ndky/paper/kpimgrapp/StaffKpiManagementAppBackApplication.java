package ndky.paper.kpimgrapp;

import ndky.paper.kpimgrapp.Storage.AvatarStorageImpl;
import ndky.paper.kpimgrapp.Storage.CertificateStorageImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@MapperScan("ndky.paper.kpimgrapp.Mappers")
@SpringBootApplication
public class StaffKpiManagementAppBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(StaffKpiManagementAppBackApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public CommandLineRunner initialize(AvatarStorageImpl avatarStorage, CertificateStorageImpl certificateStorage) {
        return args -> {
            avatarStorage.createDirectory();
            certificateStorage.createDirectory();
        };
    }
}
