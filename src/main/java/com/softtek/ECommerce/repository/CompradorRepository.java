package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.model.entity.Comprador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompradorRepository extends JpaRepository<Comprador, Long> {
    Page<Comprador> findByBajaLogica(boolean bajaLogica, Pageable pageable);
}
