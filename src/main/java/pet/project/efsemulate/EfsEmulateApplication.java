package pet.project.efsemulate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EfsEmulateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EfsEmulateApplication.class, args);
    }

}
