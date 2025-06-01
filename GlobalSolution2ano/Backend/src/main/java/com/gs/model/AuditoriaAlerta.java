package com.gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "AUDITORIA_ALERTA")
public class AuditoriaAlerta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auditoria")
    @SequenceGenerator(name = "seq_auditoria", sequenceName = "seq_auditoria", allocationSize = 1)
    @Column(name = "id_auditoria")
    private Long id;

    @NotNull(message = "O alerta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_alerta", nullable = false)
    private Alerta alerta;

    @NotBlank(message = "A ação é obrigatória")
    @Column(name = "acao", nullable = false, length = 10)
    private String acao;

    @NotNull(message = "A data de modificação é obrigatória")
    @Column(name = "data_modificacao", nullable = false)
    private LocalDateTime dataModificacao;

    @NotBlank(message = "O usuário é obrigatório")
    @Column(name = "usuario", nullable = false, length = 30)
    private String usuario;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    // Construtor padrão
    public AuditoriaAlerta() {
    }

    // Construtor com parâmetros
    public AuditoriaAlerta(Alerta alerta, String acao, String usuario) {
        this.alerta = alerta;
        this.acao = acao;
        this.dataModificacao = LocalDateTime.now();
        this.usuario = usuario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    @Override
    public String toString() {
        return "AuditoriaAlerta{" +
                "id=" + id +
                ", alerta=" + alerta.getId() +
                ", acao='" + acao + '\'' +
                ", dataModificacao=" + dataModificacao +
                ", usuario='" + usuario + '\'' +
                '}';
    }
} 