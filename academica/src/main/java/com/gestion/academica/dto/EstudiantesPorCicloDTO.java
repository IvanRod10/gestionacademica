package com.gestion.academica.dto;

public class EstudiantesPorCicloDTO {
    private final String ciclo;
    private final Long cantidadEstudiantes;

    public EstudiantesPorCicloDTO(String ciclo, Long cantidadEstudiantes) {
        this.ciclo = ciclo;
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    public String getCiclo() { return ciclo; }
    public Long getCantidadEstudiantes() { return cantidadEstudiantes; }
}
