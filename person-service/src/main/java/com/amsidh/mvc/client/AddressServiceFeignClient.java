package com.amsidh.mvc.client;

import com.amsidh.mvc.model.response.address.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-client", url = "${address_service_url}")
public interface AddressServiceFeignClient {

    @GetMapping("/{addressId}")
    AddressResponse getAddressByAddressId(@PathVariable Integer addressId);
}
