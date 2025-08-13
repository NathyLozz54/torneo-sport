package co.com.torneo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"co.com.torneo", "com.torneo.infra.drive.jwt"})
public class TorneoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TorneoApplication.class, args);
    }
}
