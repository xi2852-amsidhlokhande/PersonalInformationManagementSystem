package com.amsidh.mvc.model.request.asset;

import com.amsidh.mvc.model.request.BaseRequest;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetRequest extends BaseRequest {
    @NotNull(message = "AssertId is required")
    private Integer assetId;
    @Size(min = 2, max = 20, message = "Asset Name must be in between 2 to 20 character in size")
    private String assetName;
    private String assetType;
    private Double assetValue;
}
