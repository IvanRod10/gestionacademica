package com.gestion.academica.controllers;

import com.gestion.academica.entities.Inscripcion;
import com.gestion.academica.services.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @PostMapping
    public Inscripcion crear(@RequestBody Inscripcion inscripcion) {
        return inscripcionService.crear(inscripcion);
    }

    @GetMapping
    public List<Inscripcion> obtenerTodas() {
        return inscripcionService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Inscripcion obtenerPorId(@PathVariable Long id) {
        return inscripcionService.obtenerPorId(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Inscripcion actualizar(@PathVariable Long id, @RequestBody Inscripcion inscripcion) {
        return inscripcionService.actualizar(id, inscripcion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inscripcionService.eliminar(id);
    }
}
