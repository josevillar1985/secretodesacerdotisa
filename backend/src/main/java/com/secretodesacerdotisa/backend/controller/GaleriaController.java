package com.secretodesacerdotisa.backend.controller;

import com.secretodesacerdotisa.backend.dto.GaleriaDTO;
import com.secretodesacerdotisa.backend.mapper.GaleriaMapper;
import com.secretodesacerdotisa.backend.model.Fotos;
import com.secretodesacerdotisa.backend.model.Galeria;
import com.secretodesacerdotisa.backend.repository.GaleriaRepository;
import org.springframework.http.ResponseEntity;
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

    // ================= GET =================
    @GetMapping
    public List<GaleriaDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
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

                    // 🟢 fotos (reemplazo total)
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
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Galería no existe.");
        }
        repository.deleteById(id);
    }
}
