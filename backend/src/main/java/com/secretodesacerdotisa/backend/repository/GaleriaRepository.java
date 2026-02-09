package com.secretodesacerdotisa.backend.repository;

import com.secretodesacerdotisa.backend.model.Galeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GaleriaRepository extends JpaRepository<Galeria, Long> {

    @Query("SELECT DISTINCT g FROM Galeria g LEFT JOIN FETCH g.fotos")
    List<Galeria> findAllWithFotos();
}