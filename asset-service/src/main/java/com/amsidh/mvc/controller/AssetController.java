package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.request.asset.AssetRequest;
import com.amsidh.mvc.model.request.asset.UpdateAssetRequest;
import com.amsidh.mvc.model.response.BaseResponse;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import com.amsidh.mvc.service.AssetService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/assets")
@Slf4j
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;
    private final Gson gson;

    @PostMapping
    public BaseResponse saveAsset(@Valid @RequestBody AssetRequest assetRequest) {
        log.info("Request received to save an asset with {}", gson.toJson(assetRequest));
        AssetResponse assetResponse = assetService.saveAsset(assetRequest);
        log.info("Returning response for save asset {}", gson.toJson(assetRequest));
        return BaseResponse.builder().data(assetResponse).build();
    }

    @GetMapping("/{assetId}")
    public BaseResponse getAssetById(@PathVariable Integer assetId) {
        log.info("Received request to get Asset by assetId {}", assetId);
        AssetResponse assetResponse = assetService.getAssetById(assetId);
        log.info("Returning response with asset details {}", gson.toJson(assetResponse));
        return BaseResponse.builder().data(assetResponse).build();
    }

    @PatchMapping("/{assetId}")
    public BaseResponse updateAssetById(@PathVariable Integer assetId, @RequestBody UpdateAssetRequest updateAssetRequest) {
        log.info("Received request to update Asset by assetId {} and asset details {}", assetId, gson.toJson(updateAssetRequest));
        AssetResponse assetResponse = assetService.updateAsset(assetId, updateAssetRequest);
        log.info("Returning response after updating asset {}", gson.toJson(assetResponse));
        return BaseResponse.builder().data(assetResponse).build();
    }

    @DeleteMapping("/{assetId}")
    public BaseResponse deleteAssetById(@PathVariable Integer assetId) {
        log.info("Received request to delete Asset by assetId {}", assetId);
        assetService.deleteAssetById(assetId);
        log.info("Asset with assetId %d deleted successfully", assetId);
        return BaseResponse.builder().data(String.format("Asset with assetId %d deleted successfully", assetId)).build();
    }

    @GetMapping
    public BaseResponse getAllAsset() {
        log.info("Request received to get all Asset");
        List<AssetResponse> assetResponses = assetService.findAllAssets();
        log.info("Returning assets response {}", gson.toJson(assetResponses));
        return BaseResponse.builder().data(assetResponses).build();
    }

    @GetMapping("/person/{personId}")
    public BaseResponse getAssetByPersonId(@PathVariable Integer personId) {
        log.info("Received request to get all Assets by personId {}", personId);
        List<AssetResponse> assetResponses = assetService.getAllAssetByPersonId(personId);
        log.info("Returning response with asset details {}", gson.toJson(assetResponses));
        return BaseResponse.builder().data(assetResponses).build();
    }
}
