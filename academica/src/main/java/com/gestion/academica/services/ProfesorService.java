package com.gestion.academica.services;

import com.gestion.academica.dto.ProfesorDTO;
import com.gestion.academica.entities.Profesor;
import com.gestion.academica.repositories.ProfesorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    // ---- CRUD (los tuyos) ----
    public Profesor crear(Profesor profesor) { return profesorRepository.save(profesor); }
    public List<Profesor> obtenerTodos() { return profesorRepository.findAll(); }
    public Profesor obtenerPorId(Long id) { return profesorRepository.findById(id).orElse(null); }
    public Profesor actualizar(Long id, Profesor actualizado) {
        Profesor existente = obtenerPorId(id);
        if (existente == null) return null;
        existente.setCodigo(actualizado.getCodigo());
        existente.setNombreCompleto(actualizado.getNombreCompleto());
        existente.setCorreoElectronico(actualizado.getCorreoElectronico());
        return profesorRepository.save(existente);
    }
    public void eliminar(Long id) { profesorRepository.deleteById(id); }

    // ---- LECTURAS en DTO + filtros para los GET ----
    public List<ProfesorDTO> listar(String nombre, String codigo, String correo) {
        List<Profesor> base;

        if (codigo != null && !codigo.isBlank()) {
            base = profesorRepository.findByCodigo(codigo);
        } else if (correo != null && !correo.isBlank()) {
            base = profesorRepository.findByCorreoElectronicoIgnoreCase(correo);
        } else if (nombre != null && !nombre.isBlank()) {
            base = profesorRepository.findByNombreCompletoContainingIgnoreCase(nombre);
        } else {
            base = profesorRepository.findAll();
        }

        // Si llegaron más de un filtro, refinamos en memoria
        if (nombre != null && !nombre.isBlank()) {
            String n = nombre.toLowerCase();
            base = base.stream()
                    .filter(p -> p.getNombreCompleto() != null &&
                            p.getNombreCompleto().toLowerCase().contains(n))
                    .toList();
        }
        if (correo != null && !correo.isBlank()) {
            String c = correo.toLowerCase();
            base = base.stream()
                    .filter(p -> p.getCorreoElectronico() != null &&
                            p.getCorreoElectronico().toLowerCase().equals(c))
                    .toList();
        }

        return base.stream().map(this::toDTO).toList();
    }

    public ProfesorDTO obtenerDTO(Long id) {
        Profesor p = profesorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profesor no encontrado"));
        return toDTO(p);
    }

    // ---- Conversión Entidad -> DTO ----
    private ProfesorDTO toDTO(Profesor p) {
        return ProfesorDTO.builder()
                .id(p.getId())
                .codigo(p.getCodigo())
                .nombreCompleto(p.getNombreCompleto())
                .correoElectronico(p.getCorreoElectronico())
                .build();
    }
}
