package com.fst.back_etat_civil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
   // List<Region> findByNom(String nom);
	Boolean existsByCode(String code);
	Boolean existsByNom(String code);

}
