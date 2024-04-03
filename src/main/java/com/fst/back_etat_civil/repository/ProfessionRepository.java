package com.fst.back_etat_civil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Profession;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
	Boolean existsByLibelle(String libelle);
}