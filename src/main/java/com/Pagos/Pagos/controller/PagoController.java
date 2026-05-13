package com.Pagos.Pagos.controller;

import com.Pagos.Pagos.dto.PagoResponseDTO;
import com.Pagos.Pagos.model.Pago;
import com.Pagos.Pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gym/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    // Listar todos los pagos de la bd
    @GetMapping
    ResponseEntity<List<PagoResponseDTO>> listarPagos(){
        return ResponseEntity.ok(pagoService.listarAllPagos());
    }

    @GetMapping("{idPago}")
    ResponseEntity<PagoResponseDTO> buscarPagoId(@PathVariable Long idPago){
        return pagoService.buscarPorId(idPago)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filtrorun/{runpagado}")
    ResponseEntity<List<PagoResponseDTO>> buscarPagoRUN(@PathVariable String runpagado){
        return ResponseEntity.ok(pagoService.filtrarPagosRut(runpagado));
    }

}
