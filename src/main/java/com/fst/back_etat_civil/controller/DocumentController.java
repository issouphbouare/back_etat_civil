package com.fst.back_etat_civil.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fst.back_etat_civil.model.Document;
import com.fst.back_etat_civil.repository.DocumentRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/document")
public class DocumentController {
    @Autowired
    DocumentRepository documentRepository;

    @GetMapping("")
    public ResponseEntity<List<Document>> getAllDocuments(@RequestParam(required = false) String nom) {
        try {
            List<Document> documents = new ArrayList<Document>();

            if (nom == null)
                documentRepository.findAll().forEach(documents::add);
            else
                documentRepository.findByNom(nom).forEach(documents::add);

            if (documents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable("id") long id) {
        Optional<Document> documentData = documentRepository.findById(id);

        if (documentData.isPresent()) {
            return new ResponseEntity<>(documentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        try {
            Document _document = documentRepository
                    .save( document);
            return new ResponseEntity<>(_document, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/documents/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable("id") long id, @RequestBody Document document) {
        Optional<Document> documentData = documentRepository.findById(id);

        if (documentData.isPresent()) {
            Document _document = documentData.get();
            _document.setNom(document.getNom());
            _document.setType(document.getType());
            _document.setDate(document.getDate());
            return new ResponseEntity<>(documentRepository.save(_document), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<HttpStatus> deleteDocument(@PathVariable("id") long id) {
        try {
            documentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/documents")
    public ResponseEntity<HttpStatus> deleteAllDocuments() {
        try {
            documentRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
