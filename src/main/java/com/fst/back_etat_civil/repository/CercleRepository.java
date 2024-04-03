package com.fst.back_etat_civil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Cercle;
import com.fst.back_etat_civil.model.Region;

@Repository
public interface CercleRepository extends JpaRepository<Cercle, Long> {
    //List<Cercle> findByNom(String nom);
List<Cercle> findByRegion(Region region);
Boolean existsByCode(String code);
Boolean existsByNom(String code);

}
