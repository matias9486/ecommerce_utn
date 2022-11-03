package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.PersonalizacionEspecifica;
import com.softtek.ECommerce.model.entity.ProductoPersonalizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosPersonalizadosRepository extends JpaRepository<ProductoPersonalizado,Long> {
    ProductoPersonalizado findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<ProductoPersonalizado> findByBajaLogica(boolean bajaLogica, Pageable pageable);
}
