package com.fst.back_etat_civil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Cercle;
import com.fst.back_etat_civil.model.Commune;
import com.fst.back_etat_civil.model.Vqf;

@Repository
public interface VqfRepository extends JpaRepository<Vqf,Long> {
    List<Vqf> findByNom(String nom);
    List<Vqf> findById(String id);
    List<Vqf> findByCommune(Commune commune);
    Boolean existsByCode(String code);
	Boolean existsByNom(String code);


}
