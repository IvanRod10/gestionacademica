package com.gestion.academica.services;

import com.gestion.academica.dto.InscripcionDTO;
import com.gestion.academica.entities.Inscripcion;
import com.gestion.academica.repositories.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    // ---------- CRUD (entidad) ----------
    public Inscripcion crear(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    public Inscripcion actualizar(Long id, Inscripcion inscripcionActualizada) {
        Inscripcion existente = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));

        existente.setEstudiante(inscripcionActualizada.getEstudiante());
        existente.setCurso(inscripcionActualizada.getCurso());
        existente.setNotaFinal(inscripcionActualizada.getNotaFinal());
        existente.setFechaEvaluacion(inscripcionActualizada.getFechaEvaluacion());

        return inscripcionRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }

    // ---------- Lecturas (DTO) + filtros ----------
    @Transactional(readOnly = true)
    public List<InscripcionDTO> listar(Long estudianteId,
                                       Long cursoId,
                                       String ciclo,
                                       Integer semestre,
                                       BigDecimal notaMin,
                                       BigDecimal notaMax) {

        List<Inscripcion> base;
        // Usamos un filtro "principal" desde el repositorio para aprovechar índices y joins
        if (estudianteId != null && cursoId != null) {
            base = inscripcionRepository.findByEstudiante_IdAndCurso_Id(estudianteId, cursoId);
        } else if (estudianteId != null) {
            base = inscripcionRepository.findByEstudiante_Id(estudianteId);
        } else if (cursoId != null) {
            base = inscripcionRepository.findByCurso_Id(cursoId);
        } else if (ciclo != null && !ciclo.isBlank()) {
            base = inscripcionRepository.findByCurso_CicloIgnoreCase(ciclo);
        } else if (semestre != null) {
            base = inscripcionRepository.findByCurso_Semestre(semestre);
        } else {
            base = inscripcionRepository.findAll();
        }

        // Refinar en memoria con los filtros restantes (si llegaron múltiples)
        if (ciclo != null && !ciclo.isBlank()) {
            String c = ciclo.toLowerCase();
            base = base.stream()
                    .filter(i -> i.getCurso() != null
                            && i.getCurso().getCiclo() != null
                            && i.getCurso().getCiclo().toLowerCase().equals(c))
                    .toList();
        }
        if (semestre != null) {
            base = base.stream()
                    .filter(i -> i.getCurso() != null && i.getCurso().getSemestre() == semestre)
                    .toList();
        }
        if (notaMin != null) {
            base = base.stream()
                    .filter(i -> i.getNotaFinal() != null && i.getNotaFinal().compareTo(notaMin) >= 0)
                    .toList();
        }
        if (notaMax != null) {
            base = base.stream()
                    .filter(i -> i.getNotaFinal() != null && i.getNotaFinal().compareTo(notaMax) <= 0)
                    .toList();
        }

        return base.stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public InscripcionDTO obtenerDTO(Long id) {
        Inscripcion i = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inscripción no encontrada"));
        return toDTO(i);
    }

    // ---------- Conversión Entidad -> DTO ----------
    private InscripcionDTO toDTO(Inscripcion i) {
        var e = i.getEstudiante();
        var c = i.getCurso();

        String nombreCompleto = null;
        if (e != null) {
            String nombre = e.getNombre() != null ? e.getNombre() : "";
            String apellido = e.getApellido() != null ? e.getApellido() : "";
            nombreCompleto = (nombre + " " + apellido).trim();
        }

        return InscripcionDTO.builder()
                .id(i.getId())
                .estudianteId(e != null ? e.getId() : null)
                .estudianteNombreCompleto(nombreCompleto)
                .cursoId(c != null ? c.getId() : null)
                .cursoCodigo(c != null ? c.getCodigo() : null)
                .cursoNombre(c != null ? c.getNombre() : null)
                .notaFinal(i.getNotaFinal())
                .fechaEvaluacion(i.getFechaEvaluacion())
                .ciclo(c != null ? c.getCiclo() : null)
                .semestre(c != null ? c.getSemestre() : null)
                .build();
    }
}
