package com.gestion.academica.repositories;

import com.gestion.academica.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNombreCompletoContainingIgnoreCase(String nombreCompleto);
    List<Profesor> findByCodigo(String codigo);
    List<Profesor> findByCorreoElectronicoIgnoreCase(String correoElectronico);

}
