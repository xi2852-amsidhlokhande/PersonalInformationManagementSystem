package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.entity.Asset;
import com.amsidh.mvc.exception.AssetNotFoundException;
import com.amsidh.mvc.model.request.asset.AssetRequest;
import com.amsidh.mvc.model.request.asset.UpdateAssetRequest;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.repository.AssetRepository;
import com.amsidh.mvc.service.AssetService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AssetResponse saveAsset(AssetRequest assetRequest) {
        log.info("Asset saving");
        return objectMapper.convertValue(assetRepository.save(objectMapper.convertValue(assetRequest, Asset.class)), AssetResponse.class);
    }

    @Override
    public AssetResponse updateAsset(Integer assetId, UpdateAssetRequest updateAssetRequest) {
        log.info("Updating asset with assetId {} and asset details", assetId, updateAssetRequest);
        AssetResponse assetResponse = assetRepository.findById(assetId).map(asset -> {
            Optional.ofNullable(updateAssetRequest.getAssetName()).ifPresent(asset::setAssetName);
            Optional.ofNullable(updateAssetRequest.getAssetType()).ifPresent(asset::setAssetType);
            Optional.ofNullable(updateAssetRequest.getAssetValue()).ifPresent(asset::setAssetValue);
            return assetRepository.save(asset);
        }).map(updatedAsset -> objectMapper.convertValue(updatedAsset, AssetResponse.class)).orElseThrow(() -> new AssetNotFoundException(String.format("Asset with assetId %d not found", assetId)));
        log.info("Asset updated successfully");
        return assetResponse;
    }

    @Override
    public AssetResponse getAssetById(Integer assetId) {
        log.info("Getting asset with assetId {}", assetId);
        return assetRepository.findById(assetId).map(asset -> objectMapper.convertValue(asset, AssetResponse.class)).orElseThrow(() -> new AssetNotFoundException(String.format("Asset with assetId %d not found", assetId)));
    }

    @Override
    public void deleteAssetById(Integer assetId) {
        log.info("Deleting asset with assetId {}", assetId);
        assetRepository.deleteById(assetId);
        return;
    }

    @Override
    public List<AssetResponse> findAllAssets() {
        log.info("Getting all assets");
        return objectMapper.convertValue(assetRepository.findAll(), new TypeReference<>() {
        });
    }
}
