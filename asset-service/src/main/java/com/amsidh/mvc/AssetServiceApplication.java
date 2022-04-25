package com.amsidh.mvc;

import com.amsidh.mvc.entity.Asset;
import com.amsidh.mvc.model.request.asset.AssetRequest;
import com.amsidh.mvc.repository.AssetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class AssetServiceApplication implements CommandLineRunner {
	@Autowired(required = true)
	private AssetRepository assetRepository;
	@Autowired(required = true)
	private ObjectMapper objectMapper;
	@Autowired
	private Gson gson;

	public static void main(String[] args) {
		SpringApplication.run(AssetServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Cleaning Asset database");
		assetRepository.deleteAll();
		log.info("Initializing Asset database");
		List<Asset> assets = Arrays.asList(
				AssetRequest.builder().assetId(1).personId(1).assetName("Watch").assetType("Fashion").assetValue(5000d).build(),
				AssetRequest.builder().assetId(2).personId(1).assetName("Laptop").assetType("Computer").assetValue(1000d).build(),
				AssetRequest.builder().assetId(3).personId(1).assetName("Flat").assetType("Home").assetValue(3500000d).build(),
				AssetRequest.builder().assetId(4).personId(2).assetName("Cycle").assetType("Health").assetValue(5000d).build(),
				AssetRequest.builder().assetId(5).personId(3).assetName("Television").assetType("Entertainment").assetValue(150000d).build()
		).stream().map(assetRequest -> objectMapper.convertValue(assetRequest, Asset.class)).collect(Collectors.toList());
		assetRepository.saveAll(assets);
		log.info("Asset Database loaded successfully");
	}
}
