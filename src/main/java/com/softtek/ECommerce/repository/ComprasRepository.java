package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.model.entity.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprasRepository extends JpaRepository<Compra,Long> {
    Compra findByIdAndBajaLogica(Long id,Boolean bajaLogica);
    Page<Compra> findByBajaLogica(boolean bajaLogica, Pageable pageable);

}
