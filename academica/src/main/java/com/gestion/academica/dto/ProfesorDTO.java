package com.gestion.academica.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfesorDTO {
    private Long id;
    private String codigo;
    private String nombreCompleto;
    private String correoElectronico;
}
