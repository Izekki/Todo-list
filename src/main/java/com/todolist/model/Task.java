package com.todolist.model;

import java.time.LocalDateTime;

public class Task {
    private int id;
    private int userId;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaRealizacion;
    private String estado; // 'PENDIENTE' o 'TERMINADA'

    public Task() {}

    public Task(int id, int userId, String titulo, String descripcion, LocalDateTime fechaRealizacion, String estado) {
        this.id = id;
        this.userId = userId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaRealizacion = fechaRealizacion;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDateTime fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}