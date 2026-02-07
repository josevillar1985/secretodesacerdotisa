package com.secretodesacerdotisa.backend.controller;

import com.secretodesacerdotisa.backend.dto.GaleriaDTO;
import com.secretodesacerdotisa.backend.mapper.GaleriaMapper;
import com.secretodesacerdotisa.backend.model.Fotos;
import com.secretodesacerdotisa.backend.model.Galeria;
import com.secretodesacerdotisa.backend.repository.GaleriaRepository;
import jakarta.transaction.Transactional;
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

    @PostMapping
    public GaleriaDTO create(@RequestBody GaleriaDTO dto) {

        Galeria galeria = mapper.toEntity(dto);

        // 🔥 asegurar relación bidireccional
        if (galeria.getFotos() != null) {
            galeria.getFotos().forEach(foto -> foto.setGaleria(galeria));
        }

        Galeria saved = repository.save(galeria);
        return mapper.toDto(saved);
    }


    // ================= PUT =================
    // ================= PUT =================
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<GaleriaDTO> update(
            @PathVariable Long id,
            @RequestBody GaleriaDTO dto
    ) {
        return repository.findById(id)
                .map(existing -> {

                    // 🔹 campos simples
                    existing.setTitulo(dto.getTitulo());
                    existing.setDescripcion(dto.getDescripcion());
                    existing.setFecha(dto.getFecha());

                    // 🔥 sincronizar fotos (estado final = admin)
                    existing.getFotos().clear();

                    if (dto.getFotos() != null) {
                        dto.getFotos().forEach(fotoDto -> {
                            Fotos foto = new Fotos();
                            foto.setImagen(fotoDto.getImagen());
                            foto.setGaleria(existing);
                            existing.getFotos().add(foto);
                        });
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
