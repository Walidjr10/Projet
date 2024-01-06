package com.backend.springjwt.controllers;
import com.backend.springjwt.models.DemandStatus;
import com.backend.springjwt.models.Demande;
import com.backend.springjwt.models.DocumentCategorie;
import com.backend.springjwt.models.User;
import com.backend.springjwt.repository.DocumentCategorieRepository;
import com.backend.springjwt.repository.UserRepository;
import com.backend.springjwt.services.DemandeService;
import com.backend.springjwt.services.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    private final DemandeService demandeService;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DocumentCategorieRepository documentCategorieRepository;


    // Inject the DemandService through constructor injection
    public DemandeController(DemandeService demandeService) {
        this.demandeService = demandeService;
    }

    @GetMapping("/{demandId}")
    public ResponseEntity<Demande> getDemandById(@PathVariable Long demandId) {
        Demande demand = demandeService.getDemandeById(demandId);
        return ResponseEntity.ok(demand);
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
    public ResponseEntity<Stats> getDemandeCountByStatus() {
        Stats stats = demandeService.getStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
    public ResponseEntity<List<Demande>> getDemandesByStudentId(@PathVariable Long studentId) {
        Optional<List<Demande>> demandes = demandeService.getDemandsByStudentId(studentId);
            return ResponseEntity.ok(demandes.get());
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
    public ResponseEntity<List<Demande>> getAllDemandes() {
        List<Demande> demandes = demandeService.getAllDemandes();
        return ResponseEntity.ok(demandes);
    }

    @GetMapping("/list/{status}")
    @PreAuthorize("hasRole('ETUDIANT') or hasRole('ADMIN') or hasRole('DIRECTEUR') or hasRole('SECRETAIRE')")
    public ResponseEntity<List<Demande>> getAllDemandes(@PathVariable DemandStatus status) {
        List<Demande> demandes = demandeService.getDemandeByStatus(status);
        return ResponseEntity.ok(demandes);
    }


    @PostMapping("/create")
    public ResponseEntity<Demande> createDemand(@RequestBody Demande demande) {
        Optional<User> student = userRepo.findById(demande.getStudent().getId());
        Optional<DocumentCategorie> documentCategorie = documentCategorieRepository.findById(demande.getDocumentCategorie().getId());
        Optional<List<Demande>> older_demandes  = Optional.ofNullable(demandeService.getDemandeByCategorie(demande.getDocumentCategorie().getId()));
        if(student.isPresent() && documentCategorie.isPresent()){
            if(Objects.equals(documentCategorie.get().isOneTake(), "true") &&older_demandes.isPresent()&&!older_demandes.get().isEmpty()){
                return ResponseEntity.badRequest().build();
            }else{
                demande.setStudent(student.get());
                demande.setDocumentCategorie(documentCategorie.get());
                Demande createdDemand = demandeService.createDemande(demande);
                return ResponseEntity.ok(createdDemand);
            }
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{demandId}")
    public ResponseEntity<Demande> updateDemand(@PathVariable Long demandId, @RequestBody Demande demande) {
            Demande updatedDemand = demandeService.updateDemande(demandId, demande);
            return ResponseEntity.ok(updatedDemand);
    }

    @DeleteMapping("/delete/{demandId}")
    public ResponseEntity<Void> deleteDemand(@PathVariable Long demandId) {
        demandeService.deleteDemande(demandId);
        return ResponseEntity.noContent().build();
    }
}