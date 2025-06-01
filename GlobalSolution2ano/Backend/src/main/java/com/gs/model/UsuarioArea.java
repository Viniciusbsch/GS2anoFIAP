package com.gs.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "USUARIO_AREA")
@IdClass(UsuarioAreaId.class)
public class UsuarioArea {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    private Usuario usuario;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", referencedColumnName = "id_area", nullable = false)
    private AreaRisco areaRisco;

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false, nullable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    // Construtor padrão
    public UsuarioArea() {
    }

    // Construtor com parâmetros
    public UsuarioArea(Usuario usuario, AreaRisco areaRisco) {
        this.usuario = usuario;
        this.areaRisco = areaRisco;
    }

    // Getters e Setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public AreaRisco getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(AreaRisco areaRisco) {
        this.areaRisco = areaRisco;
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

        UsuarioArea that = (UsuarioArea) o;

        if (!usuario.getId().equals(that.usuario.getId())) return false;
        return areaRisco.getId().equals(that.areaRisco.getId());
    }

    @Override
    public int hashCode() {
        int result = usuario.getId().hashCode();
        result = 31 * result + areaRisco.getId().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("""
            
            Usuário: %s
            Área: %s
            Data de Criação: %s
            
            """, 
            usuario.getNome(),
            areaRisco.getNome(),
            dataCriacao);
    }
} 