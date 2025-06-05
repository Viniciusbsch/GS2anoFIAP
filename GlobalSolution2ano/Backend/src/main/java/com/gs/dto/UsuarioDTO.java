package com.gs.dto;

public class UsuarioDTO {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private double latitude;
    private double longitude;
    private boolean notifEmail;
    private boolean notifSms;
    private AreaRiscoDTO areaRisco;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isNotifEmail() {
        return notifEmail;
    }

    public void setNotifEmail(boolean notifEmail) {
        this.notifEmail = notifEmail;
    }

    public boolean isNotifSms() {
        return notifSms;
    }

    public void setNotifSms(boolean notifSms) {
        this.notifSms = notifSms;
    }

    public AreaRiscoDTO getAreaRisco() {
        return areaRisco;
    }

    public void setAreaRisco(AreaRiscoDTO areaRisco) {
        this.areaRisco = areaRisco;
    }
} 