package com.backend.springjwt.controllers;

import com.backend.springjwt.models.DemandStatus;
import com.backend.springjwt.models.Demande;
import com.backend.springjwt.models.Document;
import com.backend.springjwt.models.DocumentCategorie;
import com.backend.springjwt.payload.request.DocumentForm;
import com.backend.springjwt.repository.DemandeRepository;
import com.backend.springjwt.repository.DocumentCategorieRepository;
import com.backend.springjwt.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/documents")
@PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    private  DocumentCategorieRepository documentCategorieRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long documentId) {
        Document document = documentService.getDocumentById(documentId);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @PostMapping(value = "/create",consumes = "multipart/form-data")
    public ResponseEntity<Document> createDocument(@ModelAttribute("documentForm") DocumentForm documentForm) {
        Document document = new Document();
        document.setType(documentForm.getType());
        document.setTitle(documentForm.getTitle());
        // Save the file and set the file path in the document
        String filePath = documentService.saveFile(documentForm.getFile());
        document.setFilePath(filePath);
        // Set other properties and save the document
        Document createdDocument = documentService.createDocument(document);

        Demande demande = demandeRepository.getOne(documentForm.getDemandeId());
        demande.setDocument(createdDocument);
        demande.setStatus(DemandStatus.IN_PROGRESS);
        demandeRepository.save(demande);

        return ResponseEntity.ok(createdDocument);
    }

    @PutMapping("/update/{documentId}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long documentId, @RequestBody Document document) {
        Document updatedDocument = documentService.updateDocument(documentId, document);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/delete/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}
