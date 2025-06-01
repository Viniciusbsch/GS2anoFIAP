package com.gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALERTA")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta")
    @SequenceGenerator(name = "seq_alerta", sequenceName = "seq_alerta", allocationSize = 1)
    @Column(name = "id_alerta")
    private Long id;

    @NotNull(message = "A área de risco é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private AreaRisco areaRisco;

    @NotNull(message = "O tipo de evento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_tipo", nullable = false)
    private TipoEvento tipoEvento;

    @NotNull(message = "A data/hora é obrigatória")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "A severidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(name = "severidade", nullable = false, length = 20)
    private Severidade severidade;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(name = "descricao", columnDefinition = "CLOB", nullable = false)
    private String descricao;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @NotBlank(message = "A orientação à população é obrigatória")
    @Column(name = "orientacao_populacao", columnDefinition = "CLOB", nullable = false)
    private String orientacaoPopulacao;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    // Enum para severidade
    public enum Severidade {
        BAIXO, MÉDIO, ALTO, ALTÍSSIMO, CALAMIDADE
    }

    // Construtor padrão
    public Alerta() {
    }

    // Construtor com parâmetros
    public Alerta(AreaRisco areaRisco, TipoEvento tipoEvento, LocalDateTime dataHora, 
                 Severidade severidade, String descricao, String orientacaoPopulacao) {
        this.areaRisco = areaRisco;
        this.tipoEvento = tipoEvento;
        this.dataHora = dataHora;
        this.severidade = severidade;
        this.descricao = descricao;
        this.orientacaoPopulacao = orientacaoPopulacao;
        this.status = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Severidade getSeveridade() {
        return severidade;
    }

    public void setSeveridade(Severidade severidade) {
        this.severidade = severidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOrientacaoPopulacao() {
        return orientacaoPopulacao;
    }

    public void setOrientacaoPopulacao(String orientacaoPopulacao) {
        this.orientacaoPopulacao = orientacaoPopulacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public String toString() {
        return String.format("""
            
            ID: %d
            Área: %s
            Tipo de Evento: %s
            Severidade: %s
            Descrição: %s
            Orientação: %s
            Status: %s
            Data de Criação: %s
            
            """, 
            id,
            areaRisco.getNome(),
            tipoEvento.getDescricao(),
            severidade,
            descricao,
            orientacaoPopulacao,
            status ? "Ativo" : "Inativo",
            dataCriacao);
    }
} 