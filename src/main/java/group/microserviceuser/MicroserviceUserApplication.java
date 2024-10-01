package group.microserviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "org.example.share.config.global.entity.user",
})
public class MicroserviceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceUserApplication.class, args);
    }

}
