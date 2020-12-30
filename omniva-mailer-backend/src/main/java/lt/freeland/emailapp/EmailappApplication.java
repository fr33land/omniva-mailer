package lt.freeland.emailapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"lt.freeland"})
public class EmailappApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailappApplication.class, args);
	}

}
