package com.secretodesacerdotisa.backend.service;

import com.secretodesacerdotisa.backend.dto.FotosDTO;
import com.secretodesacerdotisa.backend.mapper.FotosMapper;
import com.secretodesacerdotisa.backend.model.Fotos;
import com.secretodesacerdotisa.backend.repository.FotosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FotosService {

    private FotosRepository repo;
    private FotosMapper fotosMapper;

    public FotosService(FotosRepository repo, FotosMapper fotosMapper) {
        this.repo = repo;
        this.fotosMapper = fotosMapper;
    }

    public List<FotosDTO> getAll() {
        return repo.findAll().stream()
                .map(fotosMapper::toDto)
                .toList();
    }

    public FotosDTO insert(FotosDTO dto) {

        Fotos foto = Fotos.builder()
                .id(dto.getId())
                .imagen(dto.getImagen())
                .build();

        Fotos guardada = repo.save(foto);
        return fotosMapper.toDto(guardada);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("foto no existe.");
        }
        repo.deleteById(id);
    }
}
