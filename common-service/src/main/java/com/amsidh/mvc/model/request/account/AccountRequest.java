package com.amsidh.mvc.model.request.account;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest extends BaseRequest {
    @NotNull(message = "AccountId is required")
    private Integer accountId;
    @Size(min = 2, max = 20, message = "Account name must be 2 to 20 character in size")
    private String accountName;
    private Double balance;

}
