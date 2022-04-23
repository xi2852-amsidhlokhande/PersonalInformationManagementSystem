package com.amsidh.mvc;

import com.amsidh.mvc.entity.Address;
import com.amsidh.mvc.repository.AddressRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static com.amsidh.mvc.model.request.address.AddressRequest.builder;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

@SpringBootApplication
@Slf4j
public class AddressServiceApplication implements CommandLineRunner {

	@Autowired(required = true)
	private AddressRepository addressRepository;
	@Autowired(required = true)
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(AddressServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Loading Addresses to database");
		this.addressRepository.deleteAll();
		List<Address> addresses = asList(builder().addressId(1).city("Pune").state("Maharashtra").pinCode(412105L).build(), builder().addressId(2).city("Magarpatta").state("Maharashtra").pinCode(412106L).build(), builder().addressId(3).city("Katepuram").state("Maharashtra").pinCode(412107L).build(), builder().addressId(4).city("Anantpur").state("AndhraPradesh").pinCode(412108L).build(), builder().addressId(5).city("Hinjewadi").state("Maharashtra").pinCode(412109L).build(), builder().addressId(6).city("New Delhi").state("Delhi").pinCode(412110L).build(), builder().addressId(7).city("Allhabad").state("Gujarat").pinCode(412111L).build(), builder().addressId(8).city("Bijapur").state("Karnataka").pinCode(412112L).build())
				.stream().map(addressRequest -> this.objectMapper.convertValue(addressRequest, Address.class)).collect(toList());

		this.addressRepository.saveAll(addresses);
		log.info("Address database initialization completed");
	}
}
