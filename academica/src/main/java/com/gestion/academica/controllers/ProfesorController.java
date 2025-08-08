package com.gestion.academica.controllers;

import com.gestion.academica.entities.Profesor;
import com.gestion.academica.services.ProfesorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private final ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.ok(profesorService.crear(profesor));
    }

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodos() {
        return ResponseEntity.ok(profesorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        Profesor profesor = profesorService.obtenerPorId(id);
        return profesor != null ? ResponseEntity.ok(profesor) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizar(@PathVariable Long id, @RequestBody Profesor p) {
        Profesor actualizado = profesorService.actualizar(id, p);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        profesorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
