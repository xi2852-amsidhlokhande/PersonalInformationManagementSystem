package com.amsidh.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amsidh.mvc.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
	List<Asset> findByPersonId(Integer personId);
}
