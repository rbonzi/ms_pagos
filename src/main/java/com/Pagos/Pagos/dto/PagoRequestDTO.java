package com.Pagos.Pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDTO {
    @NotBlank(message = "El pago debe ir asignado a un RUN")
    private String run;

    @NotNull(message = "Ingrese al ID de la membresia a pagar")
    private Long idmembresia;

}
