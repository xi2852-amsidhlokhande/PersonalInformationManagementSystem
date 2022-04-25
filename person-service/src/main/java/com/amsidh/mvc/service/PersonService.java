package com.amsidh.mvc.service;

import java.util.List;

import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.model.request.person.UpdatePersonRequest;
import com.amsidh.mvc.model.response.person.PersonResponse;

public interface PersonService {
	PersonResponse savePerson(PersonRequest personRequest);

	PersonResponse updatePersonResponse(Integer personId, UpdatePersonRequest updatePersonRequest);

	PersonResponse findPersonById(Integer personId);

	void deletePersonById(Integer personId);

	List<PersonResponse> getAllPerson();
}
