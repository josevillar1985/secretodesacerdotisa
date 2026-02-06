package com.secretodesacerdotisa.backend.model;

import jakarta.persistence.*;
import lombok.Builder;
import java.util.List;

@Entity
public class Galeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String fecha;

    @OneToMany(mappedBy = "galeria", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fotos> fotos;

    // Constructor vacío obligatorio para JPA
    public Galeria() {
    }

    // Constructor para Builder
    @Builder
    public Galeria(Long id, String titulo, String descripcion, String fecha, List<Fotos> fotos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fotos = fotos;
    }

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Fotos> getFotos() {
        return fotos;
    }

    public void setFotos(List<Fotos> fotos) {
        this.fotos = fotos;
    }
}
