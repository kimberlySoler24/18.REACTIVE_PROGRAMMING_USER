package com.todolist.reactive.user;

import com.todolist.reactive.user.models.UserEntity;
import com.todolist.reactive.user.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDat(UserRepository userRepository){
		return args -> {
			UserEntity user = UserEntity.builder().email("email@gmail.com").name("email").name("12345").build();
			userRepository.save(user);
			UserEntity userTwo = UserEntity.builder().email("prueba@gmail.com").name("prueba").name("12345").build();
			userRepository.save(user);
			UserEntity userThree = UserEntity.builder().email("test@gmail.com").name("test").name("12345").build();
			userRepository.save(user);
		};
	}

	@Bean
	public WebClient createClient(){
		return WebClient.create();
	}
}
