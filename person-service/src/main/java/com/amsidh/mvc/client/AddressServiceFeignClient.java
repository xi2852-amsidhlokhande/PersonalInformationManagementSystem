package com.amsidh.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amsidh.mvc.model.response.BaseResponse;

@FeignClient(name = "address-client", url = "${address_service_url}")
public interface AddressServiceFeignClient {

	@GetMapping("/{addressId}")
	BaseResponse getAddressByAddressId(@PathVariable Integer addressId);
}
