package com.amsidh.mvc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amsidh.mvc.entity.Person;
import com.amsidh.mvc.handler.exception.PersonDataNotFoundException;
import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.model.request.person.UpdatePersonRequest;
import com.amsidh.mvc.model.response.person.PersonResponse;
import com.amsidh.mvc.repository.PersonRepository;
import com.amsidh.mvc.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {
	private final PersonRepository personRepository;
	private final ObjectMapper objectMapper;

	@Override
	public PersonResponse savePerson(PersonRequest personRequest) {
		log.info("Saving person");
		return objectMapper.convertValue(personRepository.save(objectMapper.convertValue(personRequest, Person.class)),
				PersonResponse.class);
	}

	@Override
	public PersonResponse updatePersonResponse(Integer personId, UpdatePersonRequest updatePersonRequest) {
		log.info("Updating person with personId {} and person {}", personId, updatePersonRequest);
		return personRepository.findById(personId).map(person -> {
			Optional.ofNullable(updatePersonRequest.getPersonName()).ifPresent(person::setPersonName);
			Optional.ofNullable(updatePersonRequest.getEmailId()).ifPresent(person::setEmailId);
			Optional.ofNullable(updatePersonRequest.getGender()).ifPresent(person::setGender);
			Optional.ofNullable(updatePersonRequest.getMobileNumber()).ifPresent(person::setMobileNumber);
			return objectMapper.convertValue(personRepository.save(person), PersonResponse.class);
		}).orElseThrow(() -> new PersonDataNotFoundException("person_not_found", personId));
	}

	@Override
	public PersonResponse findPersonById(Integer personId) {
		log.info("Getting person with personId {}", personId);
		return objectMapper.convertValue(personRepository.findById(personId).orElseThrow(
				() -> new PersonDataNotFoundException("person_not_found", personId)), PersonResponse.class);
	}

	@Override
	public void deletePersonById(Integer personId) {
		log.info("Delete person with personId {}", personId);
		personRepository.deleteById(personId);
	}

	@Override
	public List<PersonResponse> getAllPerson() {
		log.info("Getting all persons");
		return objectMapper.convertValue(personRepository.findAll(), new TypeReference<>() {
		});
	}
}
