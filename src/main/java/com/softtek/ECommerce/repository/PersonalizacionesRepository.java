package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.model.entity.Personalizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalizacionesRepository extends JpaRepository<Personalizacion,Long> {
    Personalizacion findByIdAndBajaLogica(Long personalizacionId, Boolean bajaLogica);


    Personalizacion findByNombre(String nombre);
    Page<Personalizacion> findByNombreContainsAndBajaLogica(String nombre,boolean bajaLogica,Pageable pageable);
    Page<Personalizacion> findByBajaLogica(boolean bajaLogica,Pageable pageable);
    Page<Personalizacion> findByBajaLogica(boolean bajaLogica,Pageable pageable,Long tiendaId);
}
