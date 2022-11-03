package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.PersonalizacionEspecifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalizacionesEspecificasRepository extends JpaRepository<PersonalizacionEspecifica,Long> {

    PersonalizacionEspecifica findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<PersonalizacionEspecifica> findByBajaLogica(boolean bajaLogica,Pageable pageable);
}
