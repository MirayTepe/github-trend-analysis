package dev.team.githubtrendanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import org.springframework.transaction.PlatformTransactionManager;



@SpringBootApplication
public class GithubTrendAnalysisApplication {
	public static void main(String[] args) {
		SpringApplication.run(GithubTrendAnalysisApplication.class, args);
	}

}
