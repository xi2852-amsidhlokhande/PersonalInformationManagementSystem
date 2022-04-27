package com.amsidh.mvc.client;

import com.amsidh.mvc.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "person-client", url = "${person_service_url}")
public interface PersonServiceFeignClient {

    @GetMapping("/{personId}")
    BaseResponse getPersonByPersonId(@PathVariable Integer personId);

    @GetMapping
    BaseResponse getPersons();
}
