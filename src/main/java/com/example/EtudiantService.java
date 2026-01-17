package com.example;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EtudiantService {

    @Inject
    DynamoDbClient dynamoDbClient; // Injection du client DynamoDB

    private static final String TABLE_NAME = "etudiants"; // Nom de la table DynamoDB

    public Etudiant createEtudiant(Etudiant etudiant) {
        // Validation des données
        validateEtudiant(etudiant);

        // Préparer l'élément à insérer
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(String.valueOf(etudiant.getId())).build());
        item.put("nom", AttributeValue.builder().s(etudiant.getNom()).build());
        item.put("age", AttributeValue.builder().n(String.valueOf(etudiant.getAge())).build());

        try {
            PutItemRequest request = PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(item)
                    .build();
            dynamoDbClient.putItem(request);
            return etudiant; // Retourner l'étudiant créé
        } catch (DynamoDbException e) {
            throw new RuntimeException("Erreur lors de la création de l'étudiant : " + e.getMessage(), e);
        }
    }

    public Etudiant getEtudiantById(String id) {
        // Préparer la clé
        Map<String, AttributeValue> key = Map.of("id", AttributeValue.builder().s(id).build());

        try {
            GetItemRequest request = GetItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .build();

            GetItemResponse response = dynamoDbClient.getItem(request);
            if (response.hasItem()) {
                Map<String, AttributeValue> item = response.item();
                Etudiant etudiant = new Etudiant();
                etudiant.setId(item.get("id").s());
                etudiant.setNom(item.get("nom").s());
                etudiant.setAge(Integer.parseInt(item.get("age").n()));
                return etudiant;
            }
            return null; // Aucun étudiant trouvé
        } catch (DynamoDbException e) {
            throw new RuntimeException("Erreur lors de la récupération de l'étudiant : " + e.getMessage(), e);
        }
    }

    public Etudiant updateEtudiant(String id, Etudiant updatedEtudiant) {
        // Valider les données de mise à jour
        if (updatedEtudiant.getAge() != null && updatedEtudiant.getAge() < 0) {
            throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
        }

        // Préparer la mise à jour
        Map<String, AttributeValue> key = Map.of("id", AttributeValue.builder().s(id).build());
        Map<String, AttributeValueUpdate> updates = new HashMap<>();

        if (updatedEtudiant.getNom() != null) {
            updates.put("nom", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().s(updatedEtudiant.getNom()).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        if (updatedEtudiant.getAge() != null) {
            updates.put("age", AttributeValueUpdate.builder()
                    .value(AttributeValue.builder().n(String.valueOf(updatedEtudiant.getAge())).build())
                    .action(AttributeAction.PUT)
                    .build());
        }

        try {
            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .attributeUpdates(updates)
                    .build();
            dynamoDbClient.updateItem(request);
            return getEtudiantById(id);
        } catch (DynamoDbException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'étudiant : " + e.getMessage(), e);
        }
    }

    public boolean deleteEtudiant(String id) {
        // Préparer la clé
        Map<String, AttributeValue> key = Map.of("id", AttributeValue.builder().s(id).build());

        try {
            DeleteItemRequest request = DeleteItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .build();
            dynamoDbClient.deleteItem(request);
            return true;
        } catch (DynamoDbException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'étudiant : " + e.getMessage(), e);
        }
    }

    // Récupérer tous les étudiants
    public List<Etudiant> getAllEtudiants() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(TABLE_NAME)
                .build();

        try {
            ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
            return scanResponse.items().stream().map(item -> {
                Etudiant etudiant = new Etudiant();
                etudiant.setId(item.get("id").s());
                etudiant.setNom(item.get("nom").s());
                etudiant.setAge(Integer.parseInt(item.get("age").n()));
                return etudiant;
            }).collect(Collectors.toList());
        } catch (DynamoDbException e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les étudiants : " + e.getMessage(), e);
        }
    }

    // Méthode utilitaire pour valider les données de l'étudiant
    private void validateEtudiant(Etudiant etudiant) {
        if (etudiant.getId() == null) {
            throw new IllegalArgumentException("L'ID de l'étudiant ne peut pas être null.");
        }
        if (etudiant.getNom() == null || etudiant.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'étudiant ne peut pas être null ou vide.");
        }
        if (etudiant.getAge() == null || etudiant.getAge() < 0) {
            throw new IllegalArgumentException("L'âge de l'étudiant ne peut pas être null ou négatif.");
        }
    }
}
