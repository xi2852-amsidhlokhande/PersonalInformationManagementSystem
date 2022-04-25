package com.amsidh.mvc.model.response.asset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetResponse implements Serializable {
    private Integer assetId;
    private Integer personId;
    private String assetName;
    private String assetType;
    private Double assetValue;
}
