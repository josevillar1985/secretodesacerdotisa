package com.secretodesacerdotisa.backend.dto;

import lombok.Builder;
import java.util.List;

@Builder
public class GaleriaDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String fecha;
    private List<FotosDTO> fotos;

    public GaleriaDTO() {
    }

    public GaleriaDTO(Long id, String titulo, String descripcion, String fecha, List<FotosDTO> fotos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fotos = fotos;
    }

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

    public List<FotosDTO> getFotos() {
        return fotos;
    }

    public void setFotos(List<FotosDTO> fotos) {
        this.fotos = fotos;
    }
}
