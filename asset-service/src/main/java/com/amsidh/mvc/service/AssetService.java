package com.amsidh.mvc.service;

import java.util.List;

import com.amsidh.mvc.model.request.asset.AssetRequest;
import com.amsidh.mvc.model.request.asset.UpdateAssetRequest;
import com.amsidh.mvc.model.response.asset.AssetResponse;

public interface AssetService {
	AssetResponse saveAsset(AssetRequest assetRequest);

	AssetResponse updateAsset(Integer assetId, UpdateAssetRequest updateAssetRequest);

	AssetResponse getAssetById(Integer assetId);

	void deleteAssetById(Integer assetId);

	List<AssetResponse> findAllAssets();

	List<AssetResponse> getAllAssetByPersonId(Integer personId);
}
