package com.amsidh.mvc.controller;

import com.amsidh.mvc.client.AccountServiceFeignClient;
import com.amsidh.mvc.client.AddressServiceFeignClient;
import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.model.request.person.UpdatePersonRequest;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.model.response.address.AddressResponse;
import com.amsidh.mvc.model.response.person.PersonResponse;
import com.amsidh.mvc.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final ObjectMapper objectMapper;
    private final Gson gson;

    private final AddressServiceFeignClient addressServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;

    @PostMapping
    public PersonResponse savePerson(@Valid @RequestBody PersonRequest personRequest) {
        log.info("Request received for saving person {}", gson.toJson(personRequest));
        PersonResponse personResponse = personService.savePerson(personRequest);
        log.info("Returning response after saving the person {}", gson.toJson(personResponse));
        return personResponse;
    }

    @PatchMapping("/{personId}")
    public PersonResponse updatePersonById(@PathVariable Integer personId, @RequestBody UpdatePersonRequest updatePersonRequest) {
        log.info("Request received for update person by personId {} and person details {}", personId, gson.toJson(updatePersonRequest));
        PersonResponse personResponse = personService.updatePersonResponse(personId, updatePersonRequest);
        log.info("Returning response after updating Person by personId {}", gson.toJson(personResponse));
        return personResponse;
    }

    @GetMapping("/{personId}")
    public PersonResponse findPersonById(@PathVariable Integer personId) {
        log.info("Request received for getting person by personId {}", personId);
        PersonResponse personResponse = personService.findPersonById(personId);
        log.info("Returning response for get Person by personId {}", gson.toJson(personResponse));
        AddressResponse address = this.addressServiceFeignClient.getAddressByAddressId(1);
        log.info("Response received from Address-Service {}", gson.toJson(address));
        AccountResponse account = this.accountServiceFeignClient.getAccountByAccountId(1);
        log.info("Response received from Account-Service {}", gson.toJson(account));
        return personResponse;
    }

    @DeleteMapping("/{personId}")
    public String deletePersonById(@PathVariable Integer personId) {
        log.info("Request received for deleting person by personId {}", personId);
        personService.deletePersonById(personId);
        log.info("Person with personId {} deleted successfully", personId);
        return String.format("Person with personId %d deleted successfully", personId);
    }

    @GetMapping
    public List<PersonResponse> getAllPersons() {
        log.info("Request received tp get all persons");
        List<PersonResponse> personResponses = personService.getAllPerson();
        log.info("Returning response for get all Person {}", gson.toJson(personResponses));
        return personResponses;
    }
}
