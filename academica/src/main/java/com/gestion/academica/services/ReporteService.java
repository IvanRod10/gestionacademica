package com.gestion.academica.services;

import com.gestion.academica.dto.*;
import com.gestion.academica.repositories.CursoRepository;
import com.gestion.academica.repositories.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public List<ProfesorCursosDTO> cantidadCursosPorProfesor() {
        return cursoRepository.cantidadCursosPorProfesor();
    }

    public List<CursoPromedioDTO> promedioPorCurso() {
        return inscripcionRepository.promedioPorCurso();
    }

    public List<EstudiantesPorCicloDTO> estudiantesPorCiclo() {
        return inscripcionRepository.estudiantesPorCiclo();
    }

    public List<TopCursoPromedioDTO> topCursosPorPromedio(int topN) {
        return inscripcionRepository.topCursosPorPromedio(PageRequest.of(0, topN));
    }
}
