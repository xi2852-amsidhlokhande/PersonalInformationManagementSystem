package com.amsidh.mvc.model.response.address;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse implements Serializable {
	private Integer addressId;
	private String city;
	private String state;
	private Long pinCode;
}
