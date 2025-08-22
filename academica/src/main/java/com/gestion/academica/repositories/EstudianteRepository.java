package com.gestion.academica.repositories;

import com.gestion.academica.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // Filtros individuales
    List<Estudiante> findByApellidoContainingIgnoreCase(String apellido);
    List<Estudiante> findByNombreContainingIgnoreCase(String nombre);
    List<Estudiante> findByCarnetContainingIgnoreCase(String carnet);

    // (Opcional) combinación útil
    List<Estudiante> findByApellidoContainingIgnoreCaseAndNombreContainingIgnoreCase(
            String apellido, String nombre
    );
}
