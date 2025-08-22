package com.gestion.academica.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EstudianteDTO {
    private Long id;
    private String carnet;
    private String nombre;
    private String apellido;
    private Integer edad;
}
