package com.amsidh.mvc.curd.job;


import com.amsidh.mvc.client.AccountServiceFeignClient;
import com.amsidh.mvc.client.AssetServiceFeignClient;
import com.amsidh.mvc.client.PersonServiceFeignClient;
import com.amsidh.mvc.model.response.account.AccountResponse;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.model.response.person.PersonResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.DoubleStream;

@Component
@Slf4j
public class CustomerBalanceJob extends QuartzJobBean {

    @Autowired
    private PersonServiceFeignClient personServiceFeignClient;
    @Autowired
    private AccountServiceFeignClient accountServiceFeignClient;
    @Autowired
    private AssetServiceFeignClient assetServiceFeignClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        notifyCustomerBalance();
    }

    //@Scheduled(cron = "* */5 * * * *", zone = "Asia/Kolkata")  // Cron job every five minutes
    public void notifyCustomerBalance() {
        Optional.ofNullable(personServiceFeignClient.getPersons().getData()).map(convertData -> objectMapper.convertValue(convertData, new TypeReference<List<PersonResponse>>() {
        })).ifPresent(personResponses -> {
            personResponses.stream().map(PersonResponse::getPersonId).forEach(personId -> {
                Double accountBalance = getAccountBalance(personId);
                Double assetBalance = getAssetBalance(personId);
                log.info("Person with personId {} is having balance amount is {}", personId, (accountBalance + assetBalance));
            });
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
