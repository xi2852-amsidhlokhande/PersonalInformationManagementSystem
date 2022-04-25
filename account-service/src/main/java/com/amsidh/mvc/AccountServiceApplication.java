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
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@EnableFeignClients
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
				AccountRequest.builder().accountId(1).personId(1).accountName("Saving Account").accountBalance(100d).build(),
				AccountRequest.builder().accountId(2).personId(1).accountName("Credit Account").accountBalance(145d).build(),
				AccountRequest.builder().accountId(3).personId(1).accountName("Debit Account").accountBalance(985d).build(),
				AccountRequest.builder().accountId(4).personId(2).accountName("Home Loan Account").accountBalance(19500d).build(),
				AccountRequest.builder().accountId(5).personId(3).accountName("Car Account").accountBalance(10d).build(),
				AccountRequest.builder().accountId(6).personId(3).accountName("Two Wheeler Account").accountBalance(9850d).build()
		).stream().map(accountRequest -> objectMapper.convertValue(accountRequest, Account.class)).collect(Collectors.toList());
		List<Account> savedAccount = accountRepository.saveAll(accounts);
		log.info("Database loaded with account {}", gson.toJson(savedAccount));
		log.info("Account database initialization is completed successfully");
	}
}
