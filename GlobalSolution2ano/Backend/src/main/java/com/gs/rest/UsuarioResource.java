package com.gs.rest;

import com.gs.dao.UsuarioDao;
import com.gs.dto.UsuarioDTO;
import com.gs.model.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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

    @GET
    @Path("/check-email/{email}")
    public Response checkEmailExists(@PathParam("email") String email) {
        Usuario usuario = usuarioDao.findByEmail(email);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        UsuarioDTO usuarioDTO = convertToDTO(usuario);
        return Response.ok(usuarioDTO).build();
    }

    @POST
    @Path("/register")
    public Response registerUser(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setTelefone(usuarioDTO.getTelefone());
            usuario.setLatitude(0.0);
            usuario.setLongitude(0.0);
            usuario.setNotifEmail(true);
            usuario.setNotifSms(false);

            usuarioDao.save(usuario);
            
            return Response.ok(convertToDTO(usuario)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao cadastrar usuário: " + e.getMessage())
                    .build();
        }
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