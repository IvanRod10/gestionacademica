package com.gestion.academica.services;

import com.gestion.academica.entities.Profesor;
import com.gestion.academica.repositories.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public Profesor crear(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    public List<Profesor> obtenerTodos() {
        return profesorRepository.findAll();
    }

    public Profesor obtenerPorId(Long id) {
        return profesorRepository.findById(id).orElse(null);
    }

    public Profesor actualizar(Long id, Profesor actualizado) {
        Profesor existente = obtenerPorId(id);
        if (existente == null) return null;

        existente.setCodigo(actualizado.getCodigo());
        existente.setNombreCompleto(actualizado.getNombreCompleto());
        existente.setCorreoElectronico(actualizado.getCorreoElectronico());

        return profesorRepository.save(existente);
    }

    public void eliminar(Long id) {
        profesorRepository.deleteById(id);
    }
}
