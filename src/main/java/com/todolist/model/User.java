package com.todolist.model;

import java.time.LocalDate;

public class User {

    private int id;
    private String nombre, apellidoPaterno, apellidoMaterno, rfc, passwordHash;
    private LocalDate fechaNacimiento;

    public User() {}
    public User(int id, String nombre, String ap, String am, LocalDate fn, String rfc, String hash) {
        this.id = id; this.nombre = nombre; this.apellidoPaterno = ap;
        this.apellidoMaterno = am; this.fechaNacimiento = fn;
        this.rfc = rfc; this.passwordHash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
