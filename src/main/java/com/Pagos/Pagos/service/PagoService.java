package com.Pagos.Pagos.service;

import com.Pagos.Pagos.dto.PagoResponseDTO;
import com.Pagos.Pagos.model.Pago;
import com.Pagos.Pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;

    private PagoResponseDTO mapToDTO(Pago pago){
        PagoResponseDTO response = new PagoResponseDTO();
                response.setIdpago(pago.getIdPago());
                response.setRunpagado(pago.getRunPagado());
                response.setMontopagado(pago.getMontoPagado());
                response.setFechapago(LocalDateTime.now());
                response.setMembresiapagada(pago.getMembresiaPagada());

                return response;
    }

    public List<PagoResponseDTO> listarAllPagos(){
        return pagoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<PagoResponseDTO> buscarPorId(Long idpago){
        return pagoRepository.findById(idpago).map(this::mapToDTO);
    }

    public List<PagoResponseDTO> filtrarPagosRut(String runpagado){
        return pagoRepository.findByRunPagado(runpagado)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
