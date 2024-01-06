package com.backend.springjwt.controllers;

import com.backend.springjwt.models.DocumentCategorie;
import com.backend.springjwt.services.DocumentCategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/documents_categorie")
@PreAuthorize("hasRole('ADMIN')")
public class DocumentCategorieController {
    private final DocumentCategorieService categorieService;

    @Autowired
    public DocumentCategorieController(DocumentCategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/{categorieId}")
    public ResponseEntity<DocumentCategorie> getCategorieById(@PathVariable Long categorieId) {
        DocumentCategorie categorie = categorieService.getCategorieById(categorieId);
        return ResponseEntity.ok(categorie);
    }

    @PostMapping("/create")
    public ResponseEntity<DocumentCategorie> createCategorie(@RequestBody DocumentCategorie categorie) {
        DocumentCategorie createdCategorie = categorieService.createCategorie(categorie);
        return ResponseEntity.ok(createdCategorie);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ETUDIANT') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
    public ResponseEntity<List<DocumentCategorie>> getAllCategories() {
        List<DocumentCategorie> categories = categorieService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/update/{categorieId}")
    public ResponseEntity<DocumentCategorie> updateCategorie(
            @PathVariable Long categorieId,
            @RequestBody DocumentCategorie updatedCategorie) {

        DocumentCategorie categorie = categorieService.updateCategorie(categorieId, updatedCategorie);
        return ResponseEntity.ok(categorie);
    }

    @DeleteMapping("/delete/{categorieId}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long categorieId) {
        categorieService.deleteCategorie(categorieId);
        return ResponseEntity.noContent().build();
    }

}
