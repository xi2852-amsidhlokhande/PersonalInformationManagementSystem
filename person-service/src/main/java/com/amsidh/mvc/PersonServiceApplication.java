package com.amsidh.mvc;

import com.amsidh.mvc.entity.Person;
import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
@EnableFeignClients
public class PersonServiceApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(PersonServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Cleaning person database");
		this.personRepository.deleteAll();
		log.info("Initializing person database");
		List<Person> personList = Stream.of(
				PersonRequest.builder().personId(1).personName("Amsidh").gender("M").emailId("amsidhlokhande@gmail.com").mobileNumber("8108551845").build(),
				PersonRequest.builder().personId(2).personName("Anjali").gender("F").emailId("anjalilokhande@gmail.com").mobileNumber("8108551846").build(),
				PersonRequest.builder().personId(3).personName("Adithi").gender("F").emailId("adithilokhande@gmail.com").mobileNumber("8108551847").build(),
				PersonRequest.builder().personId(4).personName("Aditya").gender("M").emailId("adityalokhande@gmail.com").mobileNumber("8108551848").build(),
				PersonRequest.builder().personId(5).personName("Babasha").gender("M").emailId("babashalokhande@gmail.com").mobileNumber("8108551849").build()
		).map(personRequest -> this.objectMapper.convertValue(personRequest, Person.class)).collect(Collectors.toList());
		this.personRepository.saveAll(personList);
		log.info("Person database loaded successfully");
	}
}
