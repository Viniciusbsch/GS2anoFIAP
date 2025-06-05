package com.gs.dto;

import com.gs.model.AreaRisco;
import java.time.LocalDateTime;

public class AreaRiscoDTO {
    private Long id;
    private String nome;
    private Integer nivelRisco;
    private String historicoAlteracoes;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public AreaRiscoDTO() {
    }

    public AreaRiscoDTO(AreaRisco area) {
        this.id = area.getId();
        this.nome = area.getNome();
        this.nivelRisco = area.getNivelRisco();
        this.historicoAlteracoes = area.getHistoricoAlteracoes();
        this.latitude = area.getLatitude();
        this.longitude = area.getLongitude();
        this.dataCriacao = area.getDataCriacao();
        this.dataAtualizacao = area.getDataAtualizacao();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNivelRisco() {
        return nivelRisco;
    }

    public void setNivelRisco(Integer nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    public String getHistoricoAlteracoes() {
        return historicoAlteracoes;
    }

    public void setHistoricoAlteracoes(String historicoAlteracoes) {
        this.historicoAlteracoes = historicoAlteracoes;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
} 