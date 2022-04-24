package com.amsidh.mvc.handler;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private Date timestamp;
    private List<String> messages;
}

