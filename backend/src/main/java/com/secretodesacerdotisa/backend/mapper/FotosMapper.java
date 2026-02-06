package com.secretodesacerdotisa.backend.mapper;

import com.secretodesacerdotisa.backend.dto.FotosDTO;
import com.secretodesacerdotisa.backend.model.Fotos;
import org.springframework.stereotype.Component;

@Component
public class FotosMapper {

    public FotosDTO toDto(Fotos fotos) {
        return FotosDTO.builder()
                .id(fotos.getId())
                .imagen(fotos.getImagen())
                .build();
    }

    public Fotos toEntity(FotosDTO dto) {
        return Fotos.builder()
                .id(dto.getId())
                .imagen(dto.getImagen())
                .build();
    }
}
