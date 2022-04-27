package com.amsidh.mvc.controller;

import com.amsidh.mvc.client.AccountServiceFeignClient;
import com.amsidh.mvc.client.AddressServiceFeignClient;
import com.amsidh.mvc.client.AssetServiceFeignClient;
import com.amsidh.mvc.model.request.person.PersonRequest;
import com.amsidh.mvc.model.request.person.UpdatePersonRequest;
import com.amsidh.mvc.model.response.BaseResponse;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.model.response.address.AddressResponse;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.model.response.person.PersonResponse;
import com.amsidh.mvc.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person-service/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final ObjectMapper objectMapper;
    private final Gson gson;

    private final AddressServiceFeignClient addressServiceFeignClient;
    private final AccountServiceFeignClient accountServiceFeignClient;
    private final AssetServiceFeignClient assetServiceFeignClient;
    @PostMapping
    public BaseResponse savePerson(@Valid @RequestBody PersonRequest personRequest) {
        log.info("Request received for saving person {}", gson.toJson(personRequest));
        PersonResponse personResponse = personService.savePerson(personRequest);
        log.info("Returning response after saving the person {}", gson.toJson(personResponse));
        return BaseResponse.builder().data(personResponse).build();
    }

    @PatchMapping("/{personId}")
    public BaseResponse updatePersonById(@PathVariable Integer personId, @RequestBody UpdatePersonRequest updatePersonRequest) {
        log.info("Request received for update person by personId {} and person details {}", personId, gson.toJson(updatePersonRequest));
        PersonResponse personResponse = personService.updatePersonResponse(personId, updatePersonRequest);
        log.info("Returning response after updating Person by personId {}", gson.toJson(personResponse));
        return BaseResponse.builder().data(personResponse).build();
    }

    @GetMapping("/{personId}")
    public BaseResponse findPersonById(@PathVariable Integer personId) {
        log.info("Request received for getting person by personId {}", personId);
        PersonResponse personResponse = personService.findPersonById(personId);

        log.info("Calling Address-Service");
        BaseResponse addressBaseResponse = this.addressServiceFeignClient.getAddressByAddressId(1);
        Optional.ofNullable(addressBaseResponse.getData()).ifPresent(data -> {
            //AddressResponse addressResponse = gson.fromJson(String.valueOf(data), AddressResponse.class);
            AddressResponse addressResponse = objectMapper.convertValue(data, AddressResponse.class);
            log.info("Response received from Address-Service {}", gson.toJson(addressResponse));
        });
        Optional.ofNullable(addressBaseResponse.getErrorDetails()).ifPresent(errorDetails -> {
            log.info("Response received from Address-Service {}", gson.toJson(errorDetails));
        });

        log.info("Calling Asset-Service");
        BaseResponse assetByAssetId = assetServiceFeignClient.getAssetByAssetId(1);
        Optional.ofNullable(assetByAssetId.getData()).ifPresent(data -> {
            AssetResponse assetResponse = objectMapper.convertValue(data, AssetResponse.class);
            log.info("Response received from Asset-Service {}", gson.toJson(assetResponse));
        });


        log.info("Calling Account-Service");
        BaseResponse accountBaseResponse = this.accountServiceFeignClient.getAccountByAccountId(1);
        Optional.ofNullable(accountBaseResponse.getData()).ifPresent(accountData -> {
            AccountResponse accountResponse = objectMapper.convertValue(accountData, AccountResponse.class);
            log.info("Response received from Account-Service {}", gson.toJson(accountResponse));
        });
        Optional.ofNullable(accountBaseResponse.getErrorDetails()).ifPresent(errorDetails -> {
            log.info("Response received from Account-Service {}", gson.toJson(errorDetails));
        });

        log.info("Returning response for get Person by personId {}", gson.toJson(personResponse));
        return BaseResponse.builder().data(personResponse).build();
    }

    @DeleteMapping("/{personId}")
    public BaseResponse deletePersonById(@PathVariable Integer personId) {
        log.info("Request received for deleting person by personId {}", personId);
        personService.deletePersonById(personId);
        log.info("Person with personId {} deleted successfully", personId);
        return BaseResponse.builder().data(String.format("Person with personId %d deleted successfully", personId)).build();
    }

    @GetMapping
    public BaseResponse getAllPersons() {
        log.info("Request received tp get all persons");
        List<PersonResponse> personResponses = personService.getAllPerson();
        log.info("Returning response for get all Person {}", gson.toJson(personResponses));
        return BaseResponse.builder().data(personResponses).build();
    }
}
