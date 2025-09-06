package com.gestion.academica.dto;

public class ProfesorCursosDTO {
    private final String profesor;
    private final Long cantidadCursos;

    public ProfesorCursosDTO(String profesor, Long cantidadCursos) {
        this.profesor = profesor;
        this.cantidadCursos = cantidadCursos;
    }

    public String getProfesor() { return profesor; }
    public Long getCantidadCursos() { return cantidadCursos; }
}
