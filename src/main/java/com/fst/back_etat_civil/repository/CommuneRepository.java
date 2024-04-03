package com.fst.back_etat_civil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Cercle;
import com.fst.back_etat_civil.model.Commune;
import com.fst.back_etat_civil.model.Region;

@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
   // List<Commune> findByNom(String nom);
	List<Commune> findByCercle(Cercle cercle);
	Boolean existsByCode(String code);
	Boolean existsByNom(String code);

}
