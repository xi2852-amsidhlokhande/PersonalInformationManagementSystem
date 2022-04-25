package com.amsidh.mvc.model.request.address;

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
public class UpdateAddressRequest extends BaseRequest {
	private Integer addressId;
	private String city;
	private String state;
	private Long pinCode;
}
