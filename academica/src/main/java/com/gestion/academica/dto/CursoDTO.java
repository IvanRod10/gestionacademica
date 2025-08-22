package com.gestion.academica.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CursoDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private int creditos;
    private int semestre;
    private String ciclo;

    // Relaciones “aplanadas”
    private Long profesorId;

    private Long prerequisitoId;
    private String prerequisitoCodigo;
}
