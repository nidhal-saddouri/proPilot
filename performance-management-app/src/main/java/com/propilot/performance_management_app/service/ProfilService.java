package com.propilot.performance_management_app.service;

import com.propilot.performance_management_app.model.Profil;
import com.propilot.performance_management_app.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    // Ajouter un nouveau profil
    public Profil createProfil(Profil profil) {
        return profilRepository.save(profil);
    }

    // Mettre à jour un profil existant
    public Profil updateProfil(Long id, Profil profil) {
        Profil existingProfil = profilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
        existingProfil.setName(profil.getName());
        existingProfil.setEmail(profil.getEmail());
        existingProfil.setRole(profil.getRole());
        return profilRepository.save(existingProfil);
    }

    // Obtenir un profil par ID
    public Profil getProfil(Long id) {
        return profilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
    }

    // Obtenir tous les profils
    public List<Profil> getAllProfils() {
        return profilRepository.findAll();
    }

    // Supprimer un profil
    public void deleteProfil(Long id) {
        Profil profil = profilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profil non trouvé"));
        profilRepository.delete(profil);
    }
}
