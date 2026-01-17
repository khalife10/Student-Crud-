package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class EtudiantResourceTest {

    @Test
    public void testAddEtudiant() {
        given()
                .contentType("application/json")
                .body("{\"id\": 1, \"nom\": \"John\", \"age\": 25}") // Changement de 'name' en 'nom' et 'id' est un Long
                .when()
                .post("/etudiants")
                .then()
                .statusCode(201); // Status HTTP attendu
    }

    @Test
    public void testGetEtudiant() {
        // Pré-remplir les données
        given()
                .contentType("application/json")
                .body("{\"id\": 1, \"nom\": \"John\", \"age\": 25}") // Changement de 'name' en 'nom'
                .when()
                .post("/etudiants")
                .then()
                .statusCode(201);

        // Vérifier la récupération
        given()
                .when()
                .get("/etudiants/1")
                .then()
                .statusCode(200)
                .body("id", is(1))  // Vérifier que l'id est un Long
                .body("nom", is("John")) // Changement de 'name' en 'nom'
                .body("age", is(25));
    }

    @Test
    public void testUpdateEtudiant() {
        // Ajouter une entrée
        given()
                .contentType("application/json")
                .body("{\"id\": 1, \"nom\": \"John\", \"age\": 25}") // Changement de 'name' en 'nom'
                .when()
                .post("/etudiants")
                .then()
                .statusCode(201);

        // Mise à jour de l'étudiant
        given()
                .contentType("application/json")
                .body("{\"nom\": \"Johnny\", \"age\": 26}") // Changement de 'name' en 'nom'
                .when()
                .put("/etudiants/1")
                .then()
                .statusCode(200);

        // Vérification de la mise à jour
        given()
                .when()
                .get("/etudiants/1")
                .then()
                .statusCode(200)
                .body("nom", is("Johnny")) // Changement de 'name' en 'nom'
                .body("age", is(26));
    }

    @Test
    public void testDeleteEtudiant() {
        // Ajouter une entrée
        given()
                .contentType("application/json")
                .body("{\"id\": 1, \"nom\": \"John\", \"age\": 25}") // Changement de 'name' en 'nom'
                .when()
                .post("/etudiants")
                .then()
                .statusCode(201);

        // Supprimer l'étudiant
        given()
                .when()
                .delete("/etudiants/1")
                .then()
                .statusCode(204);

        // Vérifier la suppression
        given()
                .when()
                .get("/etudiants/1")
                .then()
                .statusCode(404);
    }
}
