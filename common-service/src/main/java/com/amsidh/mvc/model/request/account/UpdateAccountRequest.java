package com.amsidh.mvc.model.request.account;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAccountRequest extends BaseRequest {
    private Integer accountId;
    private String accountName;
    private Double balance;
}
