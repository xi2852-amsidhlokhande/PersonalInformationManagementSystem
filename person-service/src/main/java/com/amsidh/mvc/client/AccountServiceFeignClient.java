package com.amsidh.mvc.client;

import com.amsidh.mvc.model.response.account.AccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "account-client", url = "${account_service_url}")
public interface AccountServiceFeignClient {

    @GetMapping("/{accountId}")
    AccountResponse getAccountByAccountId(@PathVariable Integer accountId);
}
