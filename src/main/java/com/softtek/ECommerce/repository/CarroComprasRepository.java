package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.CarroCompras;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroComprasRepository extends JpaRepository<CarroCompras,Long> {
    CarroCompras findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<CarroCompras> findByBajaLogica(boolean bajaLogica, Pageable pageable);
}
