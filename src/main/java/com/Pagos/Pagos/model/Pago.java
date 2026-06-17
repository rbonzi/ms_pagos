package com.Pagos.Pagos.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
@Schema(description = "Entidad que representa un pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID pago generado por el sistema")
    private Long idPago;

    @Column(nullable = false, length = 9)
    @Schema(description = "RUN del cliente que paga", example = "123456789")
    private String runPagado;

    @Column(nullable = false)
    @Schema(description = "Monto pagado por el cliente")
    private Long montoPagado;

    @Column(nullable = false)
    @Schema(description = "Membresia pagada por el cliente")
    private Long membresiaPagada;

    @Column
    @Schema(description = "Fecha en la que se registró el pago")
    private LocalDateTime fechapago;

    @Column
    private String tipoPlan;

}
