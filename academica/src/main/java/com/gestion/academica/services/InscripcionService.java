package com.gestion.academica.services;

import com.gestion.academica.entities.Inscripcion;
import com.gestion.academica.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    public Inscripcion crear(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public List<Inscripcion> obtenerTodas() {
        return inscripcionRepository.findAll();
    }

    public Optional<Inscripcion> obtenerPorId(Long id) {
        return inscripcionRepository.findById(id);
    }

    public Inscripcion actualizar(Long id, Inscripcion inscripcionActualizada) {
        return inscripcionRepository.findById(id).map(inscripcion -> {
            inscripcion.setEstudiante(inscripcionActualizada.getEstudiante());
            inscripcion.setCurso(inscripcionActualizada.getCurso());
            inscripcion.setNotaFinal(inscripcionActualizada.getNotaFinal());
            inscripcion.setFechaEvaluacion(inscripcionActualizada.getFechaEvaluacion());
            return inscripcionRepository.save(inscripcion);
        }).orElse(null);
    }

    public void eliminar(Long id) {
        inscripcionRepository.deleteById(id);
    }
}

