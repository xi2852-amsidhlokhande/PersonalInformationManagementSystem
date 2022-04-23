package com.amsidh.mvc.model.request.address;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressRequest extends BaseRequest {
    @NotNull(message = "AddressId is required")
    private Integer addressId;
    @Size(min = 2, max = 10, message = "City must be 2 to 10 character in size")
    private String city;
    @Size(min = 2, max = 20, message = "City must be 2 to 10 character in size")
    private String state;
    @Min(value = 6, message = "Pincode must be minimum 6 numeric digit")
    private Long pinCode;
}
