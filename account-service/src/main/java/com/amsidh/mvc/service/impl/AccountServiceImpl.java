package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entity.Account;
import com.amsidh.mvc.exception.AccountNotFoundException;
import com.amsidh.mvc.model.request.account.AccountRequest;
import com.amsidh.mvc.model.request.account.UpdateAccountRequest;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.repository.AccountRepository;
import com.amsidh.mvc.service.AccountService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AccountResponse saveAccount(AccountRequest accountRequest) {
        log.info("Saving account");
        return objectMapper.convertValue(accountRepository.save(objectMapper.convertValue(accountRequest, Account.class)), AccountResponse.class);
    }

    @Override
    public AccountResponse updateAccount(Integer accountId, UpdateAccountRequest updateAccountRequest) {
        log.info("Updating account by accountId {}", accountId);
        Account updatedAccount = accountRepository.findById(accountId).map(account -> {
            Optional.ofNullable(updateAccountRequest.getAccountName()).ifPresent(account::setAccountName);
            Optional.ofNullable(updateAccountRequest.getBalance()).ifPresent(account::setBalance);
            return accountRepository.save(account);
        }).orElseThrow(() -> new AccountNotFoundException(String.format("Account with accountId %d not found", accountId)));
        return objectMapper.convertValue(updatedAccount, AccountResponse.class);
    }

    @Override
    public AccountResponse findByAccountId(Integer accountId) {
        log.info("Getting account by accountId {}", accountId);
        return objectMapper.convertValue(accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException(String.format("Account with accountId %d not found", accountId))), AccountResponse.class);
    }

    @Override
    public void deleteAccount(Integer accountId) {
        log.info("Deleting account by accountId {}", accountId);
        accountRepository.deleteById(accountId);
        return;
    }

    @Override
    public List<AccountResponse> getAllAccounts() {
        return objectMapper.convertValue(accountRepository.findAll(), new TypeReference<>() {
        });
    }
}
