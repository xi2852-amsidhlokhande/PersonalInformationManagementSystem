package com.amsidh.mvc.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Account implements Serializable {
	@Id
	private Integer accountId;
	@NotNull
	private Integer personId;
	@NotNull
	private String accountName;
	@NotNull
	private Double accountBalance;
}
