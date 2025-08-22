package com.gestion.academica.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InscripcionDTO {
    private Long id;

    private Long estudianteId;
    private String estudianteNombreCompleto; // nombre + apellido

    private Long cursoId;
    private String cursoCodigo;
    private String cursoNombre;

    private BigDecimal notaFinal;
    private LocalDate fechaEvaluacion;

    // Atributos derivados desde Curso (útiles para filtrar/exponer)
    private String ciclo;
    private Integer semestre;
}
