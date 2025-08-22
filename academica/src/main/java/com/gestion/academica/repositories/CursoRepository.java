package com.gestion.academica.repositories;

import com.gestion.academica.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Filtros individuales
    List<Curso> findBySemestre(int semestre);
    List<Curso> findByCicloIgnoreCase(String ciclo);
    List<Curso> findByCodigoContainingIgnoreCase(String codigo);
    List<Curso> findByNombreContainingIgnoreCase(String nombre);

    // Combinaciones
    List<Curso> findBySemestreAndCicloIgnoreCase(int semestre, String ciclo);

    // Por relación
    List<Curso> findByProfesor_Id(Long profesorId);
}
