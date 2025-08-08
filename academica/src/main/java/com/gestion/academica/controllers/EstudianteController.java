package com.gestion.academica.controllers;

import com.gestion.academica.entities.Estudiante;
import com.gestion.academica.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante e) {
        return ResponseEntity.ok(estudianteService.crear(e));
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodos() {
        return ResponseEntity.ok(estudianteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        Estudiante e = estudianteService.obtenerPorId(id);
        return e != null ? ResponseEntity.ok(e) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante e) {
        Estudiante actualizado = estudianteService.actualizar(id, e);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
