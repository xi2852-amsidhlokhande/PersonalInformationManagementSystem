package com.amsidh.mvc.client;

import com.amsidh.mvc.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "assetServiceFeignClient", url = "http://localhost:8084/asset-service/assets")
public interface AssetServiceFeignClient {
    @GetMapping("/{assetId}")
    BaseResponse getAssetByAssetId(@PathVariable Integer assetId);
}
