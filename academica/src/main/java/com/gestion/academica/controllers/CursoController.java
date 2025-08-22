package com.gestion.academica.controllers;

import com.gestion.academica.dto.CursoDTO;
import com.gestion.academica.entities.Curso;
import com.gestion.academica.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    // ---------- CRUD (entidad, como ya usabas) ----------
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.crear(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.actualizar(id, curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- GETs con DTO + filtros ----------
    // Ejemplos:
    //  /cursos
    //  /cursos?semestre=4
    //  /cursos?ciclo=2024-I
    //  /cursos?codigo=INF
    //  /cursos?nombre=Estructuras
    //  /cursos?profesorId=12
    //  /cursos?semestre=4&ciclo=2024-I&codigo=INF&nombre=Algoritmos&profesorId=12
    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar(
            @RequestParam(value = "semestre", required = false) Integer semestre,
            @RequestParam(value = "ciclo", required = false) String ciclo,
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "profesorId", required = false) Long profesorId
    ) {
        return ResponseEntity.ok(cursoService.listar(semestre, ciclo, codigo, nombre, profesorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.obtenerDTO(id));
    }
}
