package com.secretodesacerdotisa.backend.controller;

import com.secretodesacerdotisa.backend.dto.FotosDTO;
import com.secretodesacerdotisa.backend.service.FotosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
public class FotosController {

    private final FotosService foto;

    public FotosController(FotosService foto) {
        this.foto = foto;
    }

    @GetMapping
    public List<FotosDTO> getAll() {
        return foto.getAll();
    }

    @PostMapping
    public FotosDTO insert(@RequestBody FotosDTO dto) {
        return foto.insert(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        foto.delete(id);
    }
}
