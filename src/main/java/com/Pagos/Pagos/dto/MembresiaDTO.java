package com.Pagos.Pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembresiaDTO {
    private String tipoPlan;
    @NotNull(message = "El precio no puede estar vacio")
    @Positive(message = "El precio tiene que ser mayor a 0")
    private Long precio;
}
