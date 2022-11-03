package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.AreaPersonalizacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface AreasDePersonalizacionRepository extends JpaRepository<AreaPersonalizacion,Long> {
    AreaPersonalizacion findByNombre(String nombre) throws ResourceInvalidException;

    AreaPersonalizacion findByIdAndBajaLogica(Long id, boolean estado);

    List<AreaPersonalizacion> findByBajaLogicaAndNombre(boolean bajaLogica, String nombre);

    Page<AreaPersonalizacion> findByBajaLogicaAndNombreContains(boolean bajaLogica,String nombre,Pageable pageable);

    Page<AreaPersonalizacion> findByBajaLogica(boolean bajaLogica,Pageable pageable);

}
