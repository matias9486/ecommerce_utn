package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.TipoPersonalizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoPersonalizacionRepository extends JpaRepository<TipoPersonalizacion,Long> {
    TipoPersonalizacion findByNombre(String nombre) throws ResourceInvalidException;

    TipoPersonalizacion findByIdAndBajaLogica(Long id, boolean estado);

    Page<TipoPersonalizacion> findByBajaLogicaAndNombreContains(boolean bajaLogica, String nombre, Pageable pageable);

    Page<TipoPersonalizacion> findByBajaLogica(boolean bajaLogica,Pageable pageable);
}
