package com.amsidh.mvc.model.request.person;

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
public class UpdatePersonRequest extends BaseRequest {
	private String personName;
	private String gender;
	private String mobileNumber;
	private String emailId;
}
