package com.amsidh.mvc.model.request.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.amsidh.mvc.model.request.BaseRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequest extends BaseRequest {
	@NotNull(message = "AccountId is required")
	private Integer accountId;
	@NotNull(message = "PersonId is required")
	private Integer personId;

	@Size(min = 2, max = 30, message = "Account name must be 2 to 30 character in size")
	private String accountName;
	private Double accountBalance;

}
