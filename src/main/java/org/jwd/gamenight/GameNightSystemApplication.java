package org.jwd.gamenight;

import org.hibernate.SessionFactory;
import org.jwd.gamenight.config.ConnectionConfig;
import org.jwd.gamenight.config.SecurityConfig;
import org.jwd.gamenight.config.TemplateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableJpaRepositories
@Import({TemplateConfig.class, ConnectionConfig.class, SecurityConfig.class})
public class GameNightSystemApplication 
{	
	public static void main(String[] args) 
	{
		SpringApplication.run(GameNightSystemApplication.class, args);
	}
}
