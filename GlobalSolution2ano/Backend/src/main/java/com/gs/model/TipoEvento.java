package com.gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TIPO_EVENTO")
public class TipoEvento {
    @Id
    @Column(name = "cod_tipo", length = 30)
    private String codigo;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @OneToMany(mappedBy = "tipoEvento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AreaTipoEvento> areas = new HashSet<>();

    @OneToMany(mappedBy = "tipoEvento", cascade = CascadeType.ALL)
    private Set<Alerta> alertas = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtor padrão
    public TipoEvento() {
    }

    // Construtor com parâmetros
    public TipoEvento(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<AreaTipoEvento> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaTipoEvento> areas) {
        this.areas = areas;
    }

    public Set<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(Set<Alerta> alertas) {
        this.alertas = alertas;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    // Métodos auxiliares
    public void adicionarArea(AreaRisco area) {
        AreaTipoEvento areaTipoEvento = new AreaTipoEvento(area, this);
        areas.add(areaTipoEvento);
        area.getTiposEvento().add(areaTipoEvento);
    }

    public void removerArea(AreaRisco area) {
        AreaTipoEvento areaTipoEvento = new AreaTipoEvento(area, this);
        areas.remove(areaTipoEvento);
        area.getTiposEvento().remove(areaTipoEvento);
    }

    @Override
    public String toString() {
        return "TipoEvento{" +
                "codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
} 