package com.secretodesacerdotisa.backend.controller;

import com.secretodesacerdotisa.backend.dto.GaleriaDTO;
import com.secretodesacerdotisa.backend.mapper.GaleriaMapper;
import com.secretodesacerdotisa.backend.model.Fotos;
import com.secretodesacerdotisa.backend.model.Galeria;
import com.secretodesacerdotisa.backend.repository.GaleriaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galerias")
@CrossOrigin
public class GaleriaController {

    private final GaleriaRepository repository;
    private final GaleriaMapper mapper;

    public GaleriaController(GaleriaRepository repository, GaleriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // ================= GET ALL =================
    @GetMapping
    public List<GaleriaDTO> getAll() {
        return repository.findAllWithFotos()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ResponseEntity<GaleriaDTO> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= POST =================
    @PostMapping
    public GaleriaDTO create(@RequestBody GaleriaDTO dto) {

        Galeria galeria = mapper.toEntity(dto);

        // 🔥 asegurar relación bidireccional
        if (galeria.getFotos() != null) {
            galeria.getFotos().forEach(f -> f.setGaleria(galeria));
        }

        Galeria saved = repository.save(galeria);
        return mapper.toDto(saved);
    }

    // ================= PUT =================
    @PutMapping("/{id}")
    public ResponseEntity<GaleriaDTO> update(
            @PathVariable Long id,
            @RequestBody GaleriaDTO dto
    ) {
        return repository.findById(id)
                .map(existing -> {

                    // 🟢 campos simples
                    existing.setTitulo(dto.getTitulo());
                    existing.setDescripcion(dto.getDescripcion());
                    existing.setFecha(dto.getFecha());

                    // 🟢 reemplazo de fotos
                    if (dto.getFotos() != null) {

                        existing.getFotos().clear();

                        List<Fotos> nuevasFotos = mapper.toEntity(dto).getFotos();
                        nuevasFotos.forEach(f -> f.setGaleria(existing));

                        existing.getFotos().addAll(nuevasFotos);
                    }

                    Galeria saved = repository.save(existing);
                    return ResponseEntity.ok(mapper.toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
