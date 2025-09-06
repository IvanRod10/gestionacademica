package com.gestion.academica.repositories;

import com.gestion.academica.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gestion.academica.dto.CursoPromedioDTO;
import com.gestion.academica.dto.EstudiantesPorCicloDTO;
import com.gestion.academica.dto.TopCursoPromedioDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    // Filtros por relaciones
    List<Inscripcion> findByEstudiante_Id(Long estudianteId);
    List<Inscripcion> findByCurso_Id(Long cursoId);
    List<Inscripcion> findByEstudiante_IdAndCurso_Id(Long estudianteId, Long cursoId);

    // Filtros por atributos del curso relacionado
    List<Inscripcion> findByCurso_CicloIgnoreCase(String ciclo);
    List<Inscripcion> findByCurso_Semestre(int semestre);
    @Query("""
    SELECT new com.gestion.academica.dto.CursoPromedioDTO(
        c.nombre,
        AVG(i.notaFinal)
    )
    FROM Inscripcion i
    JOIN i.curso c
    WHERE i.notaFinal IS NOT NULL
    GROUP BY c.id, c.nombre
    ORDER BY c.nombre
""")
    List<CursoPromedioDTO> promedioPorCurso();

    // Reporte 3: estudiantes inscritos por ciclo
    @Query("""
    SELECT new com.gestion.academica.dto.EstudiantesPorCicloDTO(
        c.ciclo,
        COUNT(DISTINCT i.estudiante.id)
    )
    FROM Inscripcion i
    JOIN i.curso c
    GROUP BY c.ciclo
    ORDER BY c.ciclo
""")
    List<EstudiantesPorCicloDTO> estudiantesPorCiclo();

    // Reporte 4: top-N cursos con mayor promedio
    @Query("""
    SELECT new com.gestion.academica.dto.TopCursoPromedioDTO(
        c.nombre,
        AVG(i.notaFinal)
    )
    FROM Inscripcion i
    JOIN i.curso c
    WHERE i.notaFinal IS NOT NULL
    GROUP BY c.id, c.nombre
    ORDER BY AVG(i.notaFinal) DESC
""")
    List<TopCursoPromedioDTO> topCursosPorPromedio(Pageable pageable);
}
