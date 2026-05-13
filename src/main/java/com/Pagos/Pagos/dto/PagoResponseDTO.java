package com.Pagos.Pagos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PagoResponseDTO {
    private Long idpago;
    private String runpagado;
    private Long montopagado;
    private LocalDateTime fechapago;
    private Long membresiapagada;
}
