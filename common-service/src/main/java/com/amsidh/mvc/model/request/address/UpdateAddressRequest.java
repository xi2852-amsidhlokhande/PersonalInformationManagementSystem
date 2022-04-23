package com.amsidh.mvc.model.request.address;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAddressRequest extends BaseRequest {
    private Integer addressId;
    private String city;
    private String state;
    private Long pinCode;
}
