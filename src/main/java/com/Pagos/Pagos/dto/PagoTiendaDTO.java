package com.Pagos.Pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoTiendaDTO {
    @NotNull(message = "Debe añadir la id de un producto")
    private Long idProducto;
    private String nombreProducto;
    private Long precioProducto;
}
