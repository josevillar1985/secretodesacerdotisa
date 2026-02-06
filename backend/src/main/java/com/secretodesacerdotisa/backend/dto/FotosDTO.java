package com.secretodesacerdotisa.backend.dto;
import lombok.Builder;

@Builder
public class FotosDTO {

    private Long id;
    private String imagen;
    private String titulo;
    private String descripcion;
    private String fecha;

    public FotosDTO(Long id, String imagen, String titulo, String descripcion, String fecha) {
        this.id = id;
        this.imagen = imagen;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public FotosDTO() {

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
}
