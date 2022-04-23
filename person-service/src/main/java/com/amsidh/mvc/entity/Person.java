package com.amsidh.mvc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

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
