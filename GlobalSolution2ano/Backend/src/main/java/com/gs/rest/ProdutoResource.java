package com.gs.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/produtos")
public class ProdutoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getProdutos() {
        return "{\"message\": \"Lista de produtos\"}";
    }
} 