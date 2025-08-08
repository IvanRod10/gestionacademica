package com.gestion.academica.services;

import com.gestion.academica.entities.Estudiante;
import com.gestion.academica.repositories.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public Estudiante crear(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    public Estudiante obtenerPorId(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    public Estudiante actualizar(Long id, Estudiante actualizado) {
        Estudiante existente = obtenerPorId(id);
        if (existente == null) return null;

        existente.setCarnet(actualizado.getCarnet());
        existente.setNombre(actualizado.getNombre());
        existente.setApellido(actualizado.getApellido());
        existente.setFechaNacimiento(actualizado.getFechaNacimiento());

        return estudianteRepository.save(existente);
    }

    public void eliminar(Long id) {
        estudianteRepository.deleteById(id);
    }
}
