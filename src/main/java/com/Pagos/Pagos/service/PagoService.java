package com.Pagos.Pagos.service;

import com.Pagos.Pagos.dto.MembresiaDTO;
import com.Pagos.Pagos.dto.PagoRequestDTO;
import com.Pagos.Pagos.dto.PagoResponseDTO;
import com.Pagos.Pagos.model.Pago;
import com.Pagos.Pagos.repository.PagoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {
    @Autowired

    private final WebClient.Builder webClientBuilder;
    private final PagoRepository pagoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    private PagoResponseDTO mapToDTO(Pago pago){
        PagoResponseDTO response = new PagoResponseDTO();
                response.setIdpago(pago.getIdPago());
                response.setRunpagado(pago.getRunPagado());
                response.setMontopagado(pago.getMontoPagado());
                response.setFechapago(pago.getFechapago());
                response.setTipoPlan(pago.getTipoPlan());
                response.setMembresiapagada(pago.getMembresiaPagada());

                return response;
    }

    // Listar todos los pagos
    public List<PagoResponseDTO> listarAllPagos(){
        return pagoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar pagos por el id de pago
    public Optional<PagoResponseDTO> buscarPorId(Long idpago){
        return pagoRepository.findById(idpago).map(this::mapToDTO);
    }

    // Filtrar pagos por run de usuario
    public List<PagoResponseDTO> filtrarPagosRut(String runpagado){
        return pagoRepository.findByRunPagado(runpagado)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Añadir pagos
    public PagoResponseDTO registrarPago(@Valid @RequestBody PagoRequestDTO dto){
            PagoRequestDTO usuario = webClientBuilder.build()
                    .get()
                    .uri("http://USUARIO/gym/socios/busqueda/" + dto.getRun())
                    //.header("Authorization", token)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), response -> Mono.empty())
                    .onStatus(status -> status.is5xxServerError(), response ->
                            Mono.error(new RuntimeException("Error interno en ms de Usuarios")))
                    .bodyToMono(PagoRequestDTO.class)
                    .onErrorResume(e -> Mono.empty())
                    .block();

            boolean usuarioExiste = usuario != null;

            if(!usuarioExiste){
                throw new RuntimeException("Socio con RUN " + dto.getRun() + " no encontrado");
            }

        // Obtener datos de la membresia
        MembresiaDTO membresia = webClientBuilder.build()
                .get()
                .uri("http://MEMBRESIAS/api/membresias/" + dto.getIdmembresia())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.empty())
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error interno al buscar la membresia el pago")))
                .bodyToMono(MembresiaDTO.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        if(membresia == null) {
            throw new RuntimeException("Membresia con el id: " + dto.getIdmembresia() + " no encontrada");
        }

        webClientBuilder.build()
                .put()
                .uri("http://USUARIO/gym/socios/procesarpago/" + dto.getRun())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("nombreMembresia", membresia.getTipoPlan()))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.empty())
                .onStatus(status -> status.is5xxServerError(), response ->
                        Mono.error(new RuntimeException("Error interno al procesar el pago")))
                .bodyToMono(Void.class)
                .onErrorResume(e -> Mono.empty())
                .block();


        System.out.println(membresia.getTipoPlan()+ " " + membresia.getPrecio() );
        Pago pagoNuevo = new Pago();
            pagoNuevo.setRunPagado(dto.getRun());
            pagoNuevo.setMontoPagado(membresia.getPrecio());
            pagoNuevo.setMembresiaPagada(dto.getIdmembresia());
            pagoNuevo.setFechapago(LocalDateTime.now());
            pagoNuevo.setTipoPlan(membresia.getTipoPlan());
            pagoRepository.save(pagoNuevo);
            return mapToDTO(pagoNuevo);
    }
}
