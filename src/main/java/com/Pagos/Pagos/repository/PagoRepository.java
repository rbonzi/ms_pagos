package com.Pagos.Pagos.repository;

import com.Pagos.Pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByRunPagado(String runPagado);
}
//<>