package com.amsidh.mvc.scheduler;


import com.amsidh.mvc.client.AccountServiceFeignClient;
import com.amsidh.mvc.client.AssetServiceFeignClient;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.model.response.person.PersonResponse;
import com.amsidh.mvc.service.PersonService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.DoubleStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerBalanceScheduler {

    private final PersonService personService;
    private final AccountServiceFeignClient accountServiceFeignClient;
    private final AssetServiceFeignClient assetServiceFeignClient;
    private final ObjectMapper objectMapper;

    @Scheduled(cron = "* */5 * * * *", zone = "Asia/Kolkata")  // Cron job every five minutes
    public void notifyCustomerBalance() {
        personService.getAllPerson().stream().map(PersonResponse::getPersonId).forEach(personId -> {
            Double accountBalance = getAccountBalance(personId);
            Double assetBalance = getAssetBalance(personId);
            log.info("Person with personId {} is having balance amount is {}", personId, (accountBalance + assetBalance));

        });
    }

    private Double getAccountBalance(Integer personId) {
        log.info("Calling Account-Service to get balance of all account");
        DoubleStream accountBalanceStream = Optional.ofNullable(accountServiceFeignClient.getAllAccountByPersonId(personId)).map(baseResponse -> {
            DoubleStream doubleStream = Optional.ofNullable(baseResponse.getData()).map(convertData -> objectMapper.convertValue(convertData, new TypeReference<List<AccountResponse>>() {
            })).stream().flatMapToDouble(new Function<List<AccountResponse>, DoubleStream>() {
                @Override
                public DoubleStream apply(List<AccountResponse> accountResponses) {
                    return accountResponses.stream().mapToDouble(AccountResponse::getAccountBalance);
                }
            });
            return doubleStream;
        }).orElseGet(() -> DoubleStream.of(0d));

        return accountBalanceStream.sum();
    }

    private Double getAssetBalance(Integer personId) {
        log.info("Calling Account-Service to get balance of all account");
        DoubleStream assetBalanceStream = Optional.ofNullable(assetServiceFeignClient.getAllAssetByPersonId(personId)).map(baseResponse -> {
            DoubleStream doubleStream = Optional.ofNullable(baseResponse.getData()).map(convertData -> objectMapper.convertValue(convertData, new TypeReference<List<AssetResponse>>() {
            })).stream().flatMapToDouble(new Function<List<AssetResponse>, DoubleStream>() {
                @Override
                public DoubleStream apply(List<AssetResponse> assetResponses) {
                    return assetResponses.stream().mapToDouble(AssetResponse::getAssetValue);
                }
            });
            return doubleStream;
        }).orElseGet(() -> DoubleStream.of(0d));

        return assetBalanceStream.sum();
    }
}
