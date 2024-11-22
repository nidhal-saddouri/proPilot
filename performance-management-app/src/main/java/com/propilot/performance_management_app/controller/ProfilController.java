package com.propilot.performance_management_app.controller;

import com.propilot.performance_management_app.model.Profil;
import com.propilot.performance_management_app.service.ProfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profils")
public class ProfilController {

    @Autowired
    private ProfilService profilService;

    // Créer un nouveau profil
    @PostMapping
    public ResponseEntity<Profil> createProfil(@Valid @RequestBody Profil profil) {
        Profil createdProfil = profilService.createProfil(profil);
        return ResponseEntity.ok(createdProfil);
    }

    // Mettre à jour un profil existant
    @PutMapping("/{id}")
    public ResponseEntity<Profil> updateProfil(@PathVariable Long id, @Valid @RequestBody Profil profil) {
        Profil updatedProfil = profilService.updateProfil(id, profil);
        return ResponseEntity.ok(updatedProfil);
    }

    // Récupérer un profil par ID
    @GetMapping("/{id}")
    public ResponseEntity<Profil> getProfil(@PathVariable Long id) {
        Profil profil = profilService.getProfil(id);
        return ResponseEntity.ok(profil);
    }

    // Récupérer tous les profils
    @GetMapping
    public ResponseEntity<List<Profil>> getAllProfils() {
        List<Profil> profils = profilService.getAllProfils();
        return ResponseEntity.ok(profils);
    }

    // Supprimer un profil
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfil(@PathVariable Long id) {
        profilService.deleteProfil(id);
        return ResponseEntity.ok("Profil supprimé avec succès");
    }
}
