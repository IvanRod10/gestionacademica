package com.gestion.academica.controllers;

import com.gestion.academica.dto.ProfesorDTO;
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

    // --- CRUD igual que antes ---
    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody Profesor profesor) {
        return ResponseEntity.ok(profesorService.crear(profesor));
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

    // --- GETs con DTO + filtros ---
    // Ejemplos: /profesores?nombre=juan  /profesores?codigo=PRF001  /profesores?correo=a@b.com
    @GetMapping
    public ResponseEntity<List<ProfesorDTO>> listar(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "codigo", required = false) String codigo,
            @RequestParam(value = "correo", required = false) String correo
    ) {
        return ResponseEntity.ok(profesorService.listar(nombre, codigo, correo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerDTO(id));
    }
}
