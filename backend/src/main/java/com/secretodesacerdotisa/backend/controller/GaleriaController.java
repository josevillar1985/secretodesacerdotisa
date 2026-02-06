package com.secretodesacerdotisa.backend.controller;

import com.secretodesacerdotisa.backend.dto.GaleriaDTO;
import com.secretodesacerdotisa.backend.mapper.GaleriaMapper;
import com.secretodesacerdotisa.backend.model.Galeria;
import com.secretodesacerdotisa.backend.repository.GaleriaRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/galerias")
public class GaleriaController {

    private final GaleriaRepository repository;
    private final GaleriaMapper mapper;

    public GaleriaController(GaleriaRepository repository, GaleriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping
    public List<GaleriaDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public GaleriaDTO create(@RequestBody GaleriaDTO dto) {
        Galeria galeria = mapper.toEntity(dto);
        Galeria saved = repository.save(galeria);
        return mapper.toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Galería no existe.");
        }
        repository.deleteById(id);
    }
}
