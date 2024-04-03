package com.fst.back_etat_civil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fst.back_etat_civil.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByNom(String nom);

}
