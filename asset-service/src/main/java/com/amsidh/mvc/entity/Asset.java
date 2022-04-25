package com.amsidh.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
