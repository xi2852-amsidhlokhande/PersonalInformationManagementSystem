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
public class Asset implements Serializable {
	@Id
	private Integer assetId;
	@NotNull
	private Integer personId;
	private String assetName;
	private String assetType;
	private Double assetValue;

}
