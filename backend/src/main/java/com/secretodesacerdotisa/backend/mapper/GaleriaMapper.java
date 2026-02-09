package com.secretodesacerdotisa.backend.mapper;
import com.secretodesacerdotisa.backend.dto.GaleriaDTO;
import com.secretodesacerdotisa.backend.model.Galeria;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class GaleriaMapper {

    private final FotosMapper fotosMapper;

    public GaleriaMapper(FotosMapper fotosMapper) {
        this.fotosMapper = fotosMapper;
    }

    public GaleriaDTO toDto(Galeria galeria) {
        return GaleriaDTO.builder()
                .id(galeria.getId())
                .titulo(galeria.getTitulo())
                .descripcion(galeria.getDescripcion())
                .fecha(galeria.getFecha())
                .fotos(galeria.getFotos() != null ? galeria.getFotos()
                        .stream()
                        .map(fotosMapper::toDto)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public Galeria toEntity(GaleriaDTO dto) {
        Galeria galeria = new Galeria();
        galeria.setId(dto.getId());
        galeria.setTitulo(dto.getTitulo());
        galeria.setDescripcion(dto.getDescripcion());
        galeria.setFecha(dto.getFecha());
        if (dto.getFotos() != null) {
            galeria.setFotos(dto.getFotos()
                    .stream()
                    .map(fotosMapper::toEntity)
                    .peek(f -> f.setGaleria(galeria))
                    .collect(Collectors.toList()));
        }
        return galeria;
    }
}
