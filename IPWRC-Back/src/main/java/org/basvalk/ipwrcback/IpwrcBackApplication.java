package org.basvalk.ipwrcback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.basvalk.ipwrcback")
@EnableJpaRepositories(basePackages = "org.basvalk.ipwrcback.Repository")
public class IpwrcBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IpwrcBackApplication.class, args);
	}

}
