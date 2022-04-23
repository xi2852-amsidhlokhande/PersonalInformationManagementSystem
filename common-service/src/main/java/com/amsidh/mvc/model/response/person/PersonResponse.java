package com.amsidh.mvc.model.response.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
