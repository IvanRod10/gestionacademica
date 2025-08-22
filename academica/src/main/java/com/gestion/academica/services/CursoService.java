package com.gestion.academica.services;

import com.gestion.academica.dto.CursoDTO;
import com.gestion.academica.entities.Curso;
import com.gestion.academica.repositories.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;

    // ---------- CRUD (entidad) ----------
    public Curso crear(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Curso actualizar(Long id, Curso actualizado) {
        Curso existente = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

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
        if (!cursoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
        cursoRepository.deleteById(id);
    }

    // ---------- Lecturas (DTO) + filtros ----------
    @Transactional(readOnly = true)
    public List<CursoDTO> listar(Integer semestre,
                                 String ciclo,
                                 String codigo,
                                 String nombre,
                                 Long profesorId) {
        List<Curso> base;

        // Filtro "principal" desde el repositorio
        if (profesorId != null) {
            base = cursoRepository.findByProfesor_Id(profesorId);
        } else if (semestre != null && ciclo != null && !ciclo.isBlank()) {
            base = cursoRepository.findBySemestreAndCicloIgnoreCase(semestre, ciclo);
        } else if (semestre != null) {
            base = cursoRepository.findBySemestre(semestre);
        } else if (ciclo != null && !ciclo.isBlank()) {
            base = cursoRepository.findByCicloIgnoreCase(ciclo);
        } else if (codigo != null && !codigo.isBlank()) {
            base = cursoRepository.findByCodigoContainingIgnoreCase(codigo);
        } else if (nombre != null && !nombre.isBlank()) {
            base = cursoRepository.findByNombreContainingIgnoreCase(nombre);
        } else {
            base = cursoRepository.findAll();
        }

        // Refinos en memoria si llegaron múltiples filtros
        if (codigo != null && !codigo.isBlank()) {
            String needle = codigo.toLowerCase();
            base = base.stream()
                    .filter(c -> c.getCodigo() != null && c.getCodigo().toLowerCase().contains(needle))
                    .toList();
        }
        if (nombre != null && !nombre.isBlank()) {
            String nn = nombre.toLowerCase();
            base = base.stream()
                    .filter(c -> c.getNombre() != null && c.getNombre().toLowerCase().contains(nn))
                    .toList();
        }
        if (semestre != null) {
            int s = semestre;
            base = base.stream()
                    .filter(c -> c.getSemestre() == s)
                    .toList();
        }
        if (ciclo != null && !ciclo.isBlank()) {
            String cc = ciclo.toLowerCase();
            base = base.stream()
                    .filter(c -> c.getCiclo() != null && c.getCiclo().toLowerCase().equals(cc))
                    .toList();
        }
        if (profesorId != null) {
            long p = profesorId;
            base = base.stream()
                    .filter(c -> c.getProfesor() != null && c.getProfesor().getId() != null && c.getProfesor().getId() == p)
                    .toList();
        }

        return base.stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CursoDTO obtenerDTO(Long id) {
        Curso c = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
        return toDTO(c);
    }

    // ---------- Conversión Entidad -> DTO ----------
    private CursoDTO toDTO(Curso c) {
        return CursoDTO.builder()
                .id(c.getId())
                .codigo(c.getCodigo())
                .nombre(c.getNombre())
                .creditos(c.getCreditos())
                .semestre(c.getSemestre())
                .ciclo(c.getCiclo())
                .profesorId(c.getProfesor() != null ? c.getProfesor().getId() : null)
                .prerequisitoId(c.getPrerequisito() != null ? c.getPrerequisito().getId() : null)
                .prerequisitoCodigo(c.getPrerequisito() != null ? c.getPrerequisito().getCodigo() : null)
                .build();
    }
}
