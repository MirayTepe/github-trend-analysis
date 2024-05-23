package dev.team.githubtrendanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.team.githubtrendanalysis")
public class GithubTrendAnalysisApplication {
	public static void main(String[] args) {
		SpringApplication.run(GithubTrendAnalysisApplication.class, args);
	}
}
