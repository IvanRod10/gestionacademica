package com.gestion.academica.controllers;

import com.gestion.academica.dto.*;
import com.gestion.academica.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // Reporte 1
    @GetMapping("/profesores/cursos")
    public ResponseEntity<List<ProfesorCursosDTO>> cantidadCursosPorProfesor() {
        return ResponseEntity.ok(reporteService.cantidadCursosPorProfesor());
    }

    // Reporte 2
    @GetMapping("/cursos/promedio")
    public ResponseEntity<List<CursoPromedioDTO>> promedioPorCurso() {
        return ResponseEntity.ok(reporteService.promedioPorCurso());
    }

    // Reporte 3
    @GetMapping("/inscripciones/por-ciclo")
    public ResponseEntity<List<EstudiantesPorCicloDTO>> estudiantesPorCiclo() {
        return ResponseEntity.ok(reporteService.estudiantesPorCiclo());
    }

    // Reporte 4 (top 3 por default)
    @GetMapping("/cursos/top-promedios")
    public ResponseEntity<List<TopCursoPromedioDTO>> topCursosPorPromedio(
            @RequestParam(defaultValue = "3") int top) {
        return ResponseEntity.ok(reporteService.topCursosPorPromedio(top));
    }
}
