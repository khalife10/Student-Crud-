package com.example;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    @Inject
    EtudiantService etudiantService;

    @POST
    public Response createEtudiant(@Valid Etudiant etudiant) {
        Etudiant createdEtudiant = etudiantService.createEtudiant(etudiant);
        return Response.status(Response.Status.CREATED).entity(createdEtudiant).build();
    }

    @GET
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @GET
    @Path("/{id}")
    public Etudiant getEtudiant(@PathParam("id") Long id) {
        // Convertir l'ID de Long à String (si nécessaire)
        Etudiant etudiant = etudiantService.getEtudiantById(String.valueOf(id));
        if (etudiant == null) {
            throw new NotFoundException("Étudiant introuvable pour l'ID : " + id);
        }
        return etudiant;
    }

    @PUT
    @Path("/{id}")
    public Response updateEtudiant(@PathParam("id") Long id, @Valid Etudiant updatedEtudiant) {
        // Convertir l'ID de Long à String (si nécessaire)
        Etudiant etudiant = etudiantService.updateEtudiant(String.valueOf(id), updatedEtudiant);
        if (etudiant == null) {
            throw new NotFoundException("Étudiant introuvable pour l'ID : " + id);
        }
        return Response.ok(etudiant).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteEtudiant(@PathParam("id") Long id) {
        // Convertir l'ID de Long à String (si nécessaire)
        boolean deleted = etudiantService.deleteEtudiant(String.valueOf(id));
        if (!deleted) {
            throw new NotFoundException("Étudiant introuvable pour l'ID : " + id);
        }
        return Response.noContent().build();
    }
}
