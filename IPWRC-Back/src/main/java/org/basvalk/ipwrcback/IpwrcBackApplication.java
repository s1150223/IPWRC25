package org.basvalk.ipwrcback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication(scanBasePackages = "org.basvalk.ipwrcback")
public class IpwrcBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpwrcBackApplication.class, args);
        System.out.println("🚀 IpwrcBackApplication started successfully!");
    }

    @Bean
    public CommandLineRunner verifySecurityChain(@Autowired(required = false) SecurityFilterChain filterChain) {
        return args -> {
            if (filterChain != null) {
                System.out.println("✅ Custom SecurityFilterChain is active and loaded!");
            } else {
                System.out.println("❌ No custom SecurityFilterChain found — default security is active!");
            }
        };
    }
}
