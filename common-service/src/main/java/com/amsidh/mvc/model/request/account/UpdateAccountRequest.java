package com.amsidh.mvc.model.request.account;

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
public class UpdateAccountRequest extends BaseRequest {
	private String accountName;
	private Double balance;
}
