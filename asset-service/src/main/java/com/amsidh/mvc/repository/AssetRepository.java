package com.amsidh.mvc.repository;

import com.amsidh.mvc.entity.Asset;
import com.amsidh.mvc.model.response.asset.AssetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByPersonId(Integer personId);
}
