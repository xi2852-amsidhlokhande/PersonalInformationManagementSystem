package com.amsidh.mvc.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amsidh.mvc.entity.Address;
import com.amsidh.mvc.handler.exception.AddressDataNotFoundException;
import com.amsidh.mvc.model.request.address.AddressRequest;
import com.amsidh.mvc.model.request.address.UpdateAddressRequest;
import com.amsidh.mvc.model.response.address.AddressResponse;
import com.amsidh.mvc.repository.AddressRepository;
import com.amsidh.mvc.service.AddressService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
	private final AddressRepository addressRepository;
	private final ObjectMapper objectMapper;

	@Override
	public AddressResponse saveAddress(AddressRequest addressRequest) {
		log.info("Saving address");
		Address address = objectMapper.convertValue(addressRequest, Address.class);
		return objectMapper.convertValue(addressRepository.save(address), AddressResponse.class);
	}

	@Override
	public AddressResponse updateAddress(Integer addressId, UpdateAddressRequest updateAddressRequest) {
		log.info("Updating updateAddressRequest by updateAddressRequest id" + addressId);
		return addressRepository.findById(addressId).map(oldAddress -> {
			Optional.ofNullable(updateAddressRequest.getCity()).ifPresent(oldAddress::setCity);
			Optional.ofNullable(updateAddressRequest.getState()).ifPresent(oldAddress::setState);
			Optional.ofNullable(updateAddressRequest.getPinCode()).ifPresent(oldAddress::setPinCode);
			return objectMapper.convertValue(addressRepository.save(oldAddress), AddressResponse.class);
		}).orElseThrow(() -> new AddressDataNotFoundException(
				String.format("Address with addressId %d not found", addressId)));
	}

	@Override
	public AddressResponse findByAddressId(Integer addressId) {
		log.info("Finding address by id" + addressId);
		Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressDataNotFoundException(
				String.format("Address with addressId %d not found", addressId)));
		return objectMapper.convertValue(address, AddressResponse.class);
	}

	@Override
	public void deleteAddress(Integer addressId) {
		log.info("Deleting address by id" + addressId);
		Address address = addressRepository.findById(addressId).orElseThrow(() -> new AddressDataNotFoundException(
				String.format("Address with addressId %d not found", addressId)));
		addressRepository.delete(address);
		return;
	}

	@Override
	public List<AddressResponse> getAllAddress() {
		log.info("Finding all address");
		return objectMapper.convertValue(addressRepository.findAll(), new TypeReference<>() {
		});
	}
}
