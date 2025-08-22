package com.gestion.academica.controllers;

import com.gestion.academica.dto.EstudianteDTO;
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

    // --- CRUD (igual que los tenías) ---
    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody Estudiante e) {
        return ResponseEntity.ok(estudianteService.crear(e));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long id, @RequestBody Estudiante e) {
        Estudiante actualizado = estudianteService.actualizar(id, e);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        estudianteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --- GETs con DTO + filtros ---
    // Ejemplos:
    //   /estudiantes
    //   /estudiantes?apellido=Perez
    //   /estudiantes?nombre=Juan
    //   /estudiantes?carnet=2024-001
    //   /estudiantes?apellido=Perez&nombre=Juan
    @GetMapping
    public ResponseEntity<List<EstudianteDTO>> listar(
            @RequestParam(value = "apellido", required = false) String apellido,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "carnet", required = false) String carnet
    ) {
        return ResponseEntity.ok(estudianteService.listar(apellido, nombre, carnet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.obtenerDTO(id));
    }
}
