package com.gs.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Email(message = "Email inválido")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @NotNull(message = "A preferência de notificação por email é obrigatória")
    @Column(name = "notif_email", nullable = false)
    private Boolean notifEmail = true;

    @NotNull(message = "A preferência de notificação por SMS é obrigatória")
    @Column(name = "notif_sms", nullable = false)
    private Boolean notifSms = false;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioArea> areas = new HashSet<>();

    @CreationTimestamp
    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtor padrão
    public Usuario() {
    }

    // Construtor com parâmetros
    public Usuario(String nome, String email, String telefone, Boolean notifEmail, Boolean notifSms) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.notifEmail = notifEmail;
        this.notifSms = notifSms;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public Boolean getNotifEmail() {
        return notifEmail;
    }

    public void setNotifEmail(Boolean notifEmail) {
        this.notifEmail = notifEmail;
    }

    public Boolean getNotifSms() {
        return notifSms;
    }

    public void setNotifSms(Boolean notifSms) {
        this.notifSms = notifSms;
    }

    public Set<UsuarioArea> getAreas() {
        return areas;
    }

    public void setAreas(Set<UsuarioArea> areas) {
        this.areas = areas;
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
            Nome: %s
            E-mail: %s
            Telefone: %s
            Latitude: %s
            Longitude: %s
            Áreas de Risco: %s
            Notificação por E-mail: %s
            Notificação por SMS: %s
            
            """, 
            id, 
            nome, 
            email, 
            telefone,
            latitude != null ? latitude.toString() : "Nenhum",
            longitude != null ? longitude.toString() : "Nenhum",
            areas.isEmpty() ? "Nenhuma" : areas.stream()
                .map(ua -> ua.getAreaRisco().getNome())
                .collect(Collectors.joining(", ")),
            notifEmail ? "Sim" : "Não", 
            notifSms ? "Sim" : "Não");
    }
} 