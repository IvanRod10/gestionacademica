package com.gestion.academica.services;

import com.gestion.academica.entities.Curso;
import com.gestion.academica.repositories.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso crear(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> obtenerTodos() {
        return cursoRepository.findAll();
    }

    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    public Curso actualizar(Long id, Curso actualizado) {
        Curso existente = obtenerPorId(id);
        if (existente == null) return null;

        existente.setCodigo(actualizado.getCodigo());
        existente.setNombre(actualizado.getNombre());
        existente.setCreditos(actualizado.getCreditos());
        existente.setSemestre(actualizado.getSemestre());
        existente.setCiclo(actualizado.getCiclo());
        existente.setProfesor(actualizado.getProfesor());
        existente.setPrerequisito(actualizado.getPrerequisito());

        return cursoRepository.save(existente);
    }

    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}
