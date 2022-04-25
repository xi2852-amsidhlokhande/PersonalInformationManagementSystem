package com.amsidh.mvc.model.request.asset;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAssetRequest extends BaseRequest {
    private String assetName;
    private String assetType;
    private Double assetValue;
}
