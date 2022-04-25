package com.amsidh.mvc.model.response.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse implements Serializable {
    private Integer accountId;
    private Integer personId;
    private String accountName;
    private Double accountBalance;
}
