package com.amsidh.mvc.service;

import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.model.request.person.UpdatePersonRequest;
import com.amsidh.mvc.model.response.person.PersonResponse;

import java.util.List;

public interface PersonService {
    PersonResponse savePerson(PersonRequest personRequest);
    PersonResponse updatePersonResponse(Integer personId, UpdatePersonRequest updatePersonRequest);
    PersonResponse findPersonById(Integer personId);
    void deletePersonById(Integer personId);
    List<PersonResponse> getAllPerson();
}
