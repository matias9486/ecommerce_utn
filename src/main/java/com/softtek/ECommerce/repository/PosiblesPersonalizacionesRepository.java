package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.PosiblePersonalizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PosiblesPersonalizacionesRepository extends JpaRepository<PosiblePersonalizacion,Long> {
    //busco para ver que no existan posibles personalizaciones con mismo area y tipo

    @Query(value = "Select * from posible_personalizacion where area_personalizacion=? and tipo_personalizacion=? and baja_logica=0",nativeQuery = true)
    PosiblePersonalizacion findByAreaAndTipoAndBajaLogica(Long area, Long tipo,boolean estado) throws ResourceInvalidException;

    PosiblePersonalizacion findByIdAndBajaLogica(Long id, boolean estado);

    Page<PosiblePersonalizacion> findByBajaLogica(boolean bajaLogica,Pageable pageable);
}
