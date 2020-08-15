package com.example.app.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class TaskApplication {

	/** health/up check */
	@GetMapping
	public String appCheck() {
		return "yata-spring-boot api is up and running! I changed something";
	}
	
	/** Enable CORS globally */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                	.allowedMethods("GET", "POST", "PUT", "DELETE")
                	.allowedOrigins("*")
                	.allowedHeaders("*");
            }
        };
    }

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

}
