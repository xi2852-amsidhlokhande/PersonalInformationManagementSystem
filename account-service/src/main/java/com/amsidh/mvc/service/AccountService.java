package com.amsidh.mvc.service;

import com.amsidh.mvc.model.request.account.AccountRequest;
import com.amsidh.mvc.model.request.account.UpdateAccountRequest;
import com.amsidh.mvc.model.response.account.AccountResponse;

import java.util.List;

public interface AccountService {
    AccountResponse saveAccount(AccountRequest accountRequest);

    AccountResponse updateAccount(Integer accountId, UpdateAccountRequest updateAccountRequest);

    AccountResponse findByAccountId(Integer accountId);

    void deleteAccount(Integer accountId);

    List<AccountResponse> getAllAccounts();

    List<AccountResponse> getAllAccountsByPersonId(Integer personId);
}
