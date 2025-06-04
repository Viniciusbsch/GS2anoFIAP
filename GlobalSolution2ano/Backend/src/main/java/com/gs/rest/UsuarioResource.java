package com.gs.rest;

import com.gs.dao.UsuarioDao;
import com.gs.dto.UsuarioDTO;
import com.gs.model.Usuario;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDao usuarioDao;

    public UsuarioResource() {
        this.usuarioDao = new UsuarioDao();
    }

    @GET
    @Path("/{userid}")
    public Response getUserById(@PathParam("userid") Long userid) {
        Usuario usuario = usuarioDao.findById(userid);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        UsuarioDTO usuarioDTO = convertToDTO(usuario);
        return Response.ok(usuarioDTO).build();
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId().intValue());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setLatitude(usuario.getLatitude());
        dto.setLongitude(usuario.getLongitude());
        dto.setNotifEmail(usuario.getNotifEmail());
        dto.setNotifSms(usuario.getNotifSms());
        return dto;
    }
} 