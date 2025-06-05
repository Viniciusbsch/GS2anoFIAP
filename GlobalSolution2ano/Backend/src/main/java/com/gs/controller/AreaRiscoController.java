package com.gs.controller;

import com.gs.dto.AreaRiscoDTO;
import com.gs.model.AreaRisco;
import com.gs.service.AreaRiscoService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/areas")
public class AreaRiscoController {
    private final AreaRiscoService areaRiscoService;

    public AreaRiscoController() {
        this.areaRiscoService = new AreaRiscoService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarAreas() {
        try {
            List<AreaRisco> areas = areaRiscoService.listarAreas();
            List<AreaRiscoDTO> areasDTO = areas.stream()
                .map(AreaRiscoDTO::new)
                .collect(Collectors.toList());
            return Response.ok(areasDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar áreas: " + e.getMessage())
                    .build();
        }
    }
} 