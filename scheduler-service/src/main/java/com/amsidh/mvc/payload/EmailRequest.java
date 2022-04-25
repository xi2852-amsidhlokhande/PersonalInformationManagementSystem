package com.amsidh.mvc.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;
}

