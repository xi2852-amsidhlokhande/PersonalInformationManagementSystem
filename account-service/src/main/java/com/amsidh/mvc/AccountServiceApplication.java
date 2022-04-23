package com.amsidh.mvc;

import com.amsidh.mvc.entity.Account;
import com.amsidh.mvc.model.request.account.AccountRequest;
import com.amsidh.mvc.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class AccountServiceApplication implements CommandLineRunner {

	@Autowired(required = true)
	private AccountRepository accountRepository;
	@Autowired(required = true)
	private ObjectMapper objectMapper;
	@Autowired(required = true)
	private Gson gson;

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Cleaning account database");
		accountRepository.deleteAll();
		log.info("Initializing account database");
		List<Account> accounts = Arrays.asList(
				AccountRequest.builder().accountId(1).accountName("Saving Account").balance(100d).build(),
				AccountRequest.builder().accountId(2).accountName("Credit Account").balance(145d).build(),
				AccountRequest.builder().accountId(3).accountName("Debit Account").balance(985d).build(),
				AccountRequest.builder().accountId(4).accountName("Home Loan Account").balance(19500d).build(),
				AccountRequest.builder().accountId(5).accountName("Car Account").balance(10d).build(),
				AccountRequest.builder().accountId(6).accountName("Two Wheeler Account").balance(9850d).build()
		).stream().map(accountRequest -> objectMapper.convertValue(accountRequest, Account.class)).collect(Collectors.toList());
		List<Account> savedAccount = accountRepository.saveAll(accounts);
		log.info("Database loaded with account {}", gson.toJson(savedAccount));
		log.info("Account database initialization is completed successfully");
	}
}
