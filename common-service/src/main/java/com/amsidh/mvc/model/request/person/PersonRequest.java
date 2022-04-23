package com.amsidh.mvc.model.request.person;

import com.amsidh.mvc.constant.Constant;
import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRequest extends BaseRequest {

    @NotNull(message = "PersonId is required")
    private Integer personId;

    @Pattern(regexp = Constant.NAME_PATTERN, message = "Asset Name must be in between 3 to 30 character in size")
    private String personName;

    @Pattern(regexp = Constant.GENDER_PATTERN, message = "Allowed values are only m/M/male/Male/f/F/female/Female only")
    private String gender;

    @Pattern(regexp = Constant.MOBILE_PATTERN, message = "Mobile number must be minimum 10 and maximum 12 digit numeric")
    private String mobileNumber;

    @Email(message = "Email must be a valid emailId")
    private String emailId;
}
