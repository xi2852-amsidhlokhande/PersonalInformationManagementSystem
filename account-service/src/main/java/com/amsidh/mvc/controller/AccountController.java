package com.amsidh.mvc.controller;

import com.amsidh.mvc.client.AssetServiceFeignClient;
import com.amsidh.mvc.model.request.account.AccountRequest;
import com.amsidh.mvc.model.request.account.UpdateAccountRequest;
import com.amsidh.mvc.model.response.BaseResponse;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final ObjectMapper objectMapper;
    private final Gson gson;

    private final AssetServiceFeignClient assetServiceFeignClient;

    @PostMapping
    public BaseResponse saveAccount(@Valid @RequestBody AccountRequest accountRequest) {
        log.info("Request received for saving account with {}", gson.toJson(accountRequest));
        AccountResponse accountResponse = accountService.saveAccount(accountRequest);
        log.info("Returning account response {}", gson.toJson(accountResponse));
        return BaseResponse.builder().data(accountResponse).build();
    }

    @PatchMapping("/{accountId}")
    public BaseResponse updateAccount(@PathVariable Integer accountId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        log.info("Request received for update account with accountId {} and account", accountId, gson.toJson(updateAccountRequest));
        AccountResponse accountResponse = accountService.updateAccount(accountId, updateAccountRequest);
        log.info("Returning account after updating {}", gson.toJson(accountResponse));
        return BaseResponse.builder().data(accountResponse).build();
    }

    @GetMapping("/{accountId}")
    public BaseResponse getAccountById(@PathVariable Integer accountId) {
        log.info("Request received for getting account by accountId {}", accountId);
        AccountResponse accountResponse = accountService.findByAccountId(accountId);
        log.info("Returning response with account {}", accountResponse);
        BaseResponse baseResponse = this.assetServiceFeignClient.getAssetByAssetId(1);
        Optional.ofNullable(baseResponse.getData()).ifPresent(data -> {
            AssetResponse assetResponse = gson.fromJson(data.toString(), AssetResponse.class);
            log.info("Response received from asset-service {}", gson.toJson(assetResponse));
        });

        Optional.ofNullable(baseResponse.getErrorDetails()).ifPresent(errorDetails -> {
            log.info("Response received from asset-service {}", gson.toJson(errorDetails));
        });

        return BaseResponse.builder().data(accountResponse).build();
    }

    @DeleteMapping("/{accountId}")
    public BaseResponse deleteAccount(@PathVariable Integer accountId) {
        log.info("Request received to delete an account with accountId {}", accountId);
        accountService.deleteAccount(accountId);
        log.info("Account with accountId {} deleted successfully", accountId);
        return BaseResponse.builder().data(String.format("Account with accountId %d deleted successfully", accountId)).build();
    }

    @GetMapping
    public BaseResponse findAllAccount() {
        log.info("Request received to get all account");
        List<AccountResponse> accounts = accountService.getAllAccounts();
        log.info("Returning response with all account", gson.toJson(accounts));
        return BaseResponse.builder().data(accounts).build();
    }

    @GetMapping("/person/{personId}")
    public BaseResponse getAccountsByPersonId(@PathVariable Integer personId) {
        log.info("Request received for getting all accounts by personId {}", personId);
        List<AccountResponse> accountResponses = accountService.getAllAccountsByPersonId(personId);
        log.info("Returning response with all account", gson.toJson(accountResponses));
        return BaseResponse.builder().data(accountResponses).build();
    }
}
