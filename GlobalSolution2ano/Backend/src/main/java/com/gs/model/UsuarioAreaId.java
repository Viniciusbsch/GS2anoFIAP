package com.gs.model;

import java.io.Serializable;
import java.util.Objects;

public class UsuarioAreaId implements Serializable {
    private Long usuario;
    private Long areaRisco;

    public UsuarioAreaId() {
    }

    public UsuarioAreaId(Long usuario, Long areaRisco) {
        this.usuario = usuario;
        this.areaRisco = areaRisco;
    }

    public Long getUsuario() {
        return usuario;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public Long getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(Long areaRisco) {
        this.areaRisco = areaRisco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuarioAreaId that = (UsuarioAreaId) o;

        if (!Objects.equals(usuario, that.usuario)) return false;
        return Objects.equals(areaRisco, that.areaRisco);
    }

    @Override
    public int hashCode() {
        int result = usuario != null ? usuario.hashCode() : 0;
        result = 31 * result + (areaRisco != null ? areaRisco.hashCode() : 0);
        return result;
    }
} 