package com.amsidh.mvc.service;

import com.amsidh.mvc.model.request.address.AddressRequest;
import com.amsidh.mvc.model.request.address.UpdateAddressRequest;
import com.amsidh.mvc.model.response.address.AddressResponse;

import java.util.List;

public interface AddressService {
    AddressResponse saveAddress(AddressRequest addressRequest);

    AddressResponse updateAddress(Integer addressId, UpdateAddressRequest updateAddressRequest);

    AddressResponse findByAddressId(Integer addressId);

    void deleteAddress(Integer addressId);

    List<AddressResponse> getAllAddress();
}
