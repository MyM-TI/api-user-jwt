package cl.mym.api.user.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("cl.mym.api.user.jwt.entity")
@EnableJpaRepositories("cl.mym.api.user.jwt.repository")
@ServletComponentScan
public class ApiUserJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiUserJwtApplication.class, args);
	}

}
