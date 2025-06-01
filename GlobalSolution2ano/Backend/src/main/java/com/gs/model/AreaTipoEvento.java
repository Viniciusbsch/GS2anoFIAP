package com.gs.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "AREA_TIPO_EVENTO")
@IdClass(AreaTipoEventoId.class)
public class AreaTipoEvento {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_area")
    private AreaRisco areaRisco;

    @Id
    @ManyToOne
    @JoinColumn(name = "cod_tipo")
    private TipoEvento tipoEvento;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtor padrão
    public AreaTipoEvento() {
    }

    // Construtor com parâmetros
    public AreaTipoEvento(AreaRisco areaRisco, TipoEvento tipoEvento) {
        this.areaRisco = areaRisco;
        this.tipoEvento = tipoEvento;
    }

    // Getters e Setters
    public AreaRisco getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(AreaRisco areaRisco) {
        this.areaRisco = areaRisco;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaTipoEvento that = (AreaTipoEvento) o;

        if (!areaRisco.equals(that.areaRisco)) return false;
        return tipoEvento.equals(that.tipoEvento);
    }

    @Override
    public int hashCode() {
        int result = areaRisco.hashCode();
        result = 31 * result + tipoEvento.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AreaTipoEvento{" +
                "areaRisco=" + areaRisco.getNome() +
                ", tipoEvento=" + tipoEvento.getDescricao() +
                '}';
    }
} 