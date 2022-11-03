package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.Tienda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TiendaRepository extends JpaRepository<Tienda,Long> {
    Tienda findByNombreAndBajaLogica(String nombre,boolean bajaLogica) throws ResourceInvalidException;

    Tienda findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<Tienda> findByNombreContainsAndBajaLogica(String nombre,boolean bajaLogica,Pageable pageable);

    Page<Tienda> findByBajaLogica(boolean bajaLogica,Pageable pageable);
}
