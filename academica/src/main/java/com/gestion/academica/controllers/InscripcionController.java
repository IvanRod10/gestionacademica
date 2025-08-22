package com.gestion.academica.controllers;

import com.gestion.academica.dto.InscripcionDTO;
import com.gestion.academica.entities.Inscripcion;
import com.gestion.academica.services.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    // --- CRUD (devolviendo entidad para no romper tu flujo actual) ---
    @PostMapping
    public ResponseEntity<Inscripcion> crear(@RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(inscripcionService.crear(inscripcion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizar(@PathVariable Long id, @RequestBody Inscripcion inscripcion) {
        return ResponseEntity.ok(inscripcionService.actualizar(id, inscripcion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- GETs con DTO + filtros ---
    // Ejemplos:
    //   /inscripciones
    //   /inscripciones?estudianteId=1
    //   /inscripciones?cursoId=10
    //   /inscripciones?ciclo=2024-I
    //   /inscripciones?semestre=4
    //   /inscripciones?notaMin=60&notaMax=100
    //   /inscripciones?estudianteId=1&cursoId=10&ciclo=2024-I&semestre=4&notaMin=61&notaMax=90
    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> listar(
            @RequestParam(value = "estudianteId", required = false) Long estudianteId,
            @RequestParam(value = "cursoId", required = false) Long cursoId,
            @RequestParam(value = "ciclo", required = false) String ciclo,
            @RequestParam(value = "semestre", required = false) Integer semestre,
            @RequestParam(value = "notaMin", required = false) BigDecimal notaMin,
            @RequestParam(value = "notaMax", required = false) BigDecimal notaMax
    ) {
        return ResponseEntity.ok(inscripcionService.listar(estudianteId, cursoId, ciclo, semestre, notaMin, notaMax));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.obtenerDTO(id));
    }
}
