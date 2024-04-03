package com.fst.back_etat_civil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fst.back_etat_civil.model.Document;
import com.fst.back_etat_civil.repository.DocumentRepository;

import lombok.Data;

@Data
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    public Optional<Document> getDocument(final long id) {
        return documentRepository.findById(id);
    }


    public List<Document> getDocuments() {
        return documentRepository.findAll();
    }

    public void deleteDocument(final long id) {
        documentRepository.deleteById(id);
    }

    public Document saveDocument(Document document) {
        return  documentRepository.save(document);


    }
}
