package com.gestion.academica.repositories;

import com.gestion.academica.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
