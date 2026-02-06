package com.secretodesacerdotisa.backend.model;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Fotos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;

    @ManyToOne
    @JoinColumn(name = "galeria_id")
    private Galeria galeria;


    public Fotos() {
    }


    @Builder
    public Fotos(Long id, String imagen, Galeria galeria) {
        this.id = id;
        this.imagen = imagen;
        this.galeria = galeria;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }
}
