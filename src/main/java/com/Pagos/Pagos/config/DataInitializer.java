package com.Pagos.Pagos.config;

import com.Pagos.Pagos.model.Pago;
import com.Pagos.Pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final PagoRepository pagoRepository;

    @Override
    public void run(String... args){
        if(pagoRepository.count() > 0){
            log.info("Datos cargados");
            return;
        }

        log.info("No hay datos guardados, creando datos");

        // Solo para pruebas
        pagoRepository.save(new Pago(null, "204431221", 15000L, 1L)); // Membresía básica
        pagoRepository.save(new Pago(null, "18992334K", 25000L, 2L)); // Membresía premium
        pagoRepository.save(new Pago(null, "256628166", 15000L, 1L)); // El tuyo, Nano
        pagoRepository.save(new Pago(null, "153329884", 45000L, 3L)); // Membresía VIP
        pagoRepository.save(new Pago(null, "210094437", 25000L, 2L)); // Membresía premium


    }
}