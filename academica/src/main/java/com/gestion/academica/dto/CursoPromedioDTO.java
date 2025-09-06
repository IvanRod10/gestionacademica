package com.gestion.academica.dto;

public class CursoPromedioDTO {
    private final String curso;
    private final Double promedio;

    public CursoPromedioDTO(String curso, Double promedio) {
        this.curso = curso;
        this.promedio = promedio;
    }

    public String getCurso() { return curso; }
    public Double getPromedio() { return promedio; }
}
