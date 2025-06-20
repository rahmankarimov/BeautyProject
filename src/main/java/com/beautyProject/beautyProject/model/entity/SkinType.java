package com.beautyProject.beautyProject.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "skin_types")
public class SkinType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String recommendationText;

    @OneToMany(mappedBy = "skinType")
    private Set<User> users = new HashSet<>();

    @ManyToMany(mappedBy = "suitableSkinTypes")
    private Set<Product> products = new HashSet<>();



}