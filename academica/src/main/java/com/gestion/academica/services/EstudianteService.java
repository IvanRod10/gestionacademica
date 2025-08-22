package com.gestion.academica.services;

import com.gestion.academica.dto.EstudianteDTO;
import com.gestion.academica.entities.Estudiante;
import com.gestion.academica.repositories.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    // CRUD ENTIDAD
    public Estudiante crear(Estudiante estudiante) { return estudianteRepository.save(estudiante); }

    public Estudiante actualizar(Long id, Estudiante actualizado) {
        Estudiante existente = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
        existente.setCarnet(actualizado.getCarnet());
        existente.setNombre(actualizado.getNombre());
        existente.setApellido(actualizado.getApellido());
        existente.setFechaNacimiento(actualizado.getFechaNacimiento());
        return estudianteRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!estudianteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado");
        }
        estudianteRepository.deleteById(id);
    }

    // Lecturas en DTO + filtros para GET --------
    @Transactional(readOnly = true)
    public List<EstudianteDTO> listar(String apellido, String nombre, String carnet) {
        List<Estudiante> base;

        if (apellido != null && !apellido.isBlank() && nombre != null && !nombre.isBlank()) {
            base = estudianteRepository
                    .findByApellidoContainingIgnoreCaseAndNombreContainingIgnoreCase(apellido, nombre);
        } else if (apellido != null && !apellido.isBlank()) {
            base = estudianteRepository.findByApellidoContainingIgnoreCase(apellido);
        } else if (nombre != null && !nombre.isBlank()) {
            base = estudianteRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (carnet != null && !carnet.isBlank()) {
            base = estudianteRepository.findByCarnetContainingIgnoreCase(carnet);
        } else {
            base = estudianteRepository.findAll();
        }


        if (carnet != null && !carnet.isBlank()
                && (apellido != null || nombre != null)) {
            String c = carnet.toLowerCase();
            base = base.stream()
                    .filter(e -> e.getCarnet() != null && e.getCarnet().toLowerCase().contains(c))
                    .toList();
        }

        return base.stream().map(this::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public EstudianteDTO obtenerDTO(Long id) {
        Estudiante e = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
        return toDTO(e);
    }


    private EstudianteDTO toDTO(Estudiante e) {
        return EstudianteDTO.builder()
                .id(e.getId())
                .carnet(e.getCarnet())
                .nombre(e.getNombre())
                .apellido(e.getApellido())
                .edad(calcularEdad(e.getFechaNacimiento()))
                .build();
    }

    private int calcularEdad(LocalDate fechaNac) {
        return (fechaNac == null) ? 0 : Period.between(fechaNac, LocalDate.now()).getYears();
    }
}
