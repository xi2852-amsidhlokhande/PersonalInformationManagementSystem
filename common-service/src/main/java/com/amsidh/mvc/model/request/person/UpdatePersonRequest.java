package com.amsidh.mvc.model.request.person;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePersonRequest extends BaseRequest {
    private Integer personId;
    private String personName;
    private String gender;
    private String mobileNumber;
    private String emailId;
}
