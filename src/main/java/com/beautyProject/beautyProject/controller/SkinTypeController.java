package com.beautyProject.beautyProject.controller;

import com.beautyProject.beautyProject.exception.ResourceNotFoundException;
import com.beautyProject.beautyProject.model.entity.SkinType;
import com.beautyProject.beautyProject.repository.SkinTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skin-types")
public class SkinTypeController {
    private final SkinTypeRepository skinTypeRepository;

    public SkinTypeController(SkinTypeRepository skinTypeRepository) {
        this.skinTypeRepository = skinTypeRepository;
    }

    @GetMapping
    public ResponseEntity<List<SkinType>> getAllSkinTypes() {
        return ResponseEntity.ok(skinTypeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkinType> getSkinTypeById(@PathVariable Long id) {
        SkinType skinType = skinTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skin type not found with ID: " + id));
        return ResponseEntity.ok(skinType);
    }

    @PostMapping
    public ResponseEntity<SkinType> createSkinType(@RequestBody SkinType skinType) {
        return ResponseEntity.ok(skinTypeRepository.save(skinType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkinType> updateSkinType(@PathVariable Long id, @RequestBody SkinType skinTypeDetails) {
        SkinType skinType = skinTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skin type not found with ID: " + id));

        skinType.setName(skinTypeDetails.getName());
        skinType.setDescription(skinTypeDetails.getDescription());
        skinType.setRecommendationText(skinTypeDetails.getRecommendationText());

        return ResponseEntity.ok(skinTypeRepository.save(skinType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSkinType(@PathVariable Long id) {
        if (!skinTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Skin type not found with ID: " + id);
        }

        skinTypeRepository.deleteById(id);
        return ResponseEntity.ok("Skin type deleted successfully");
    }
}