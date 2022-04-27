package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.request.address.AddressRequest;
import com.amsidh.mvc.model.request.address.UpdateAddressRequest;
import com.amsidh.mvc.model.response.BaseResponse;
import com.amsidh.mvc.model.response.address.AddressResponse;
import com.amsidh.mvc.service.AddressService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address-service/addresses")
@Slf4j
public class AddressController {

	private final AddressService addressService;
	private final Gson gson;

	@PostMapping
	public BaseResponse saveAddress(@Valid @RequestBody AddressRequest addressRequest) {
		log.info("Request received to save address with address {}", gson.toJson(addressRequest));
		AddressResponse addressResponse = addressService.saveAddress(addressRequest);
		log.info("Returning response after saving address {}", gson.toJson(addressResponse));
		return BaseResponse.builder().data(addressResponse).build();
	}

	@PatchMapping("/{addressId}")
	public BaseResponse updateAddress(@PathVariable Integer addressId,
									  @Valid @RequestBody UpdateAddressRequest updateAddressRequest) {
		log.info("Request received to update an address with addressId {} and address {}", addressId,
				gson.toJson(updateAddressRequest));
		AddressResponse addressResponse = addressService.updateAddress(addressId, updateAddressRequest);
		log.info("Returning response after updating address {}", gson.toJson(addressResponse));
		return BaseResponse.builder().data(addressResponse).build();
	}

	@GetMapping("/{addressId}")
	public BaseResponse getAddressById(@PathVariable Integer addressId) {
		log.info("Request received to get an address by addressId {} ", addressId);
		AddressResponse addressResponse = addressService.findByAddressId(addressId);
		log.info("Returning response after getting an address {}", gson.toJson(addressResponse));
		return BaseResponse.builder().data(addressResponse).build();
	}

	@DeleteMapping("/{addressId}")
	public BaseResponse deleteAddressById(@PathVariable Integer addressId) {
		log.info("Request received to delete an address by addressId {} ", addressId);
		addressService.deleteAddress(addressId);
		log.info("Address with addressId {} deleted successfully", addressId);
		return BaseResponse.builder().data(String.format("Address with addressId %d deleted successfully", addressId)).build();
	}

	@GetMapping
	public BaseResponse getAllAddress() {
		log.info("Request received to get all addresses");
		List<AddressResponse> addresses = addressService.getAllAddress();
		log.info("Returning response after getting an all address {}", gson.toJson(addresses));
		return BaseResponse.builder().data(addresses).build();
	}

}
