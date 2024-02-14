package com.jeeva.blog;

import com.jeeva.blog.model.Role;
import com.jeeva.blog.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBlogAppApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyBlogAppApplication.class, args);
	}

	@Autowired
	private RoleRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		repository.save(adminRole);

		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		repository.save(userRole);


	}
}
