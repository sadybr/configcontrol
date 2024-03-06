package org.sbr.configcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class ConfigControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigControlApplication.class, args);
	}

}
