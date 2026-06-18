package com.Pagos.Pagos.repository;

import com.Pagos.Pagos.model.PagoTienda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<PagoTienda, Long> {
}
