package com.amsidh.mvc.model.response.person;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonResponse implements Serializable {
	private Integer personId;
	private String personName;
	private String gender;
	private String mobileNumber;
	private String emailId;
}
