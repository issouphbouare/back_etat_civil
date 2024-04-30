package com.fst.back_etat_civil.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Citoyen;

@Repository
public interface CitoyenRepository extends JpaRepository<Citoyen, Long> {
    //List<Citoyen> findByNom(String nom);
	Boolean existsByTelephone(String telephone);
	List<Citoyen> findByCle(String cle);
	Boolean existsByNiciv(String niciv);
	Citoyen findByNiciv(String niciv);

}
