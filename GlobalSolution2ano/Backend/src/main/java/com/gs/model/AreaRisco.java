package com.gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AREA_RISCO")
public class AreaRisco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_area_risco")
    @SequenceGenerator(name = "seq_area_risco", sequenceName = "seq_area_risco", allocationSize = 1)
    @Column(name = "id_area")
    private Long id;

    @NotBlank(message = "O nome da área é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotNull(message = "O nível de risco é obrigatório")
    @Min(value = 1, message = "O nível de risco deve estar entre 1 e 5")
    @Max(value = 5, message = "O nível de risco deve estar entre 1 e 5")
    @Column(name = "nivel_risco", nullable = false)
    private Integer nivelRisco;

    @Column(name = "historico_alteracoes", columnDefinition = "CLOB")
    private String historicoAlteracoes;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "areaRisco", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AreaTipoEvento> tiposEvento = new HashSet<>();

    @OneToMany(mappedBy = "areaRisco", cascade = CascadeType.ALL)
    private Set<Alerta> alertas = new HashSet<>();

    @OneToMany(mappedBy = "areaRisco")
    private Set<Usuario> usuarios = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Getters e Setters
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

    public Set<AreaTipoEvento> getTiposEvento() {
        return tiposEvento;
    }

    public void setTiposEvento(Set<AreaTipoEvento> tiposEvento) {
        this.tiposEvento = tiposEvento;
    }

    public Set<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(Set<Alerta> alertas) {
        this.alertas = alertas;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    // Métodos auxiliares
    public void adicionarTipoEvento(TipoEvento tipoEvento) {
        AreaTipoEvento areaTipoEvento = new AreaTipoEvento(this, tipoEvento);
        tiposEvento.add(areaTipoEvento);
        tipoEvento.getAreas().add(areaTipoEvento);
    }

    public void removerTipoEvento(TipoEvento tipoEvento) {
        AreaTipoEvento areaTipoEvento = new AreaTipoEvento(this, tipoEvento);
        tiposEvento.remove(areaTipoEvento);
        tipoEvento.getAreas().remove(areaTipoEvento);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
        usuario.setAreaRisco(this);
    }

    public void removerUsuario(Usuario usuario) {
        usuarios.remove(usuario);
        usuario.setAreaRisco(null);
    }

    @Override
    public String toString() {
        return String.format("""
            
            ID: %d
            Nome: %s
            Nível de Risco: %d
            Localização: %.6f, %.6f
            
            """, 
            id, 
            nome, 
            nivelRisco,
            latitude,
            longitude);
    }
} 