package com.gs.model;

import java.io.Serializable;
import java.util.Objects;

public class AreaTipoEventoId implements Serializable {
    private Long areaRisco;
    private String tipoEvento;

    public AreaTipoEventoId() {
    }

    public AreaTipoEventoId(Long areaRisco, String tipoEvento) {
        this.areaRisco = areaRisco;
        this.tipoEvento = tipoEvento;
    }

    public Long getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(Long areaRisco) {
        this.areaRisco = areaRisco;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaTipoEventoId that = (AreaTipoEventoId) o;

        if (!Objects.equals(areaRisco, that.areaRisco)) return false;
        return Objects.equals(tipoEvento, that.tipoEvento);
    }

    @Override
    public int hashCode() {
        int result = areaRisco != null ? areaRisco.hashCode() : 0;
        result = 31 * result + (tipoEvento != null ? tipoEvento.hashCode() : 0);
        return result;
    }
} 