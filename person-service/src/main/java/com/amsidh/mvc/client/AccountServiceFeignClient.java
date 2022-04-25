package com.amsidh.mvc.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amsidh.mvc.model.response.BaseResponse;

@FeignClient(name = "account-client", url = "${account_service_url}")
public interface AccountServiceFeignClient {

	@GetMapping("/{accountId}")
	BaseResponse getAccountByAccountId(@PathVariable Integer accountId);

	@GetMapping("/person/{personId}")
	BaseResponse getAllAccountByPersonId(@PathVariable Integer personId);
}
