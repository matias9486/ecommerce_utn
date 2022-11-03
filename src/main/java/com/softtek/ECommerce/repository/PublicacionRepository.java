package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.Publicacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {
    Publicacion findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<Publicacion> findByBajaLogica(boolean bajaLogica, Pageable pageable);

}
