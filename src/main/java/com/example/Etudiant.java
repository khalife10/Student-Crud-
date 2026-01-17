package com.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Etudiant {
    private String id;

    @NotBlank(message = "Le nom ne peut pas être vide.")
    private String nom;

    @Min(value = 0, message = "L'âge ne peut pas être négatif.")
    private Integer age; // Integer pour différencier une valeur non définie

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
