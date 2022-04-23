package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.request.account.AccountRequest;
import com.amsidh.mvc.model.request.account.UpdateAccountRequest;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.service.AccountService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final Gson gson;

    @PostMapping
    public AccountResponse saveAccount(@Valid @RequestBody AccountRequest accountRequest) {
        log.info("Request received for saving account with {}", gson.toJson(accountRequest));
        AccountResponse accountResponse = accountService.saveAccount(accountRequest);
        log.info("Returning account response {}", gson.toJson(accountResponse));
        return accountResponse;
    }

    @PatchMapping("/{accountId}")
    public AccountResponse updateAccount(@PathVariable Integer accountId, @RequestBody UpdateAccountRequest updateAccountRequest) {
        log.info("Request received for update account with accountId {} and account", accountId, gson.toJson(updateAccountRequest));
        AccountResponse accountResponse = accountService.updateAccount(accountId, updateAccountRequest);
        log.info("Returning account after updating {}", gson.toJson(accountResponse));
        return accountResponse;
    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccountById(@PathVariable Integer accountId) {
        log.info("Request received for getting account by accountId {}", accountId);
        AccountResponse accountResponse = accountService.findByAccountId(accountId);
        log.info("Returning response with account {}", accountResponse);
        return accountResponse;
    }

    @DeleteMapping("/{accountId}")
    public String deleteAccount(@PathVariable Integer accountId) {
        log.info("Request received to delete an account with accountId {}", accountId);
        accountService.deleteAccount(accountId);
        log.info("Account with accountId {} deleted successfully", accountId);
        return String.format("Account with accountId %d deleted successfully", accountId);
    }

    @GetMapping
    public List<AccountResponse> findAllAccount() {
        log.info("Request received to get all account");
        List<AccountResponse> accounts = accountService.getAllAccounts();
        log.info("Returning response with all account", gson.toJson(accounts));
        return accounts;
    }
}
