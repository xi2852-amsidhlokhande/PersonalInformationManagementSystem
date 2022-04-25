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
