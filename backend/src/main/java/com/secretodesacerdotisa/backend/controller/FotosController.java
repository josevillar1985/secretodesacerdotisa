package com.secretodesacerdotisa.backend.controller;


import com.secretodesacerdotisa.backend.dto.FotosDTO;
import com.secretodesacerdotisa.backend.service.FotosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FotosController {

    private FotosService foto;


    public FotosController(FotosService foto) {
        this.foto = foto;
    }

    @GetMapping("/todaslasfotos")
    public List<FotosDTO> getall(){
        return foto.getAll();
    }

    @PostMapping
    public FotosDTO insert(@RequestBody FotosDTO dto){
        return foto.insert(dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        foto.delete(id);
    }

}
