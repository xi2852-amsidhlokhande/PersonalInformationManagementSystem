package com.amsidh.mvc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person implements Serializable {
	@Id
	private Integer personId;
	private String personName;
	private String gender;
	private String mobileNumber;
	private String emailId;
}
