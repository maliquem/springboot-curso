package br.com.jael.springcurso.springbootcurso;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringbootCursoApplication {

	@Value("${application.name}")
	private String applicationName;

	@GetMapping(path = "/hello")
	public String helloWorld() {
		return applicationName;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCursoApplication.class, args);
	}

}
