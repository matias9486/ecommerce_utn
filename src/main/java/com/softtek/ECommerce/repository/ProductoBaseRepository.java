package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.ProductoBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductoBaseRepository extends JpaRepository<ProductoBase,Long> {
    @Query(value = "Select * from producto_base_posibles_personalizaciones where producto_base=? and posible_personalizacion=?",nativeQuery = true)
    ProductoBase findByPosiblePersonalizacion(Long productoId, Long posiblePersonalizacionID) throws ResourceInvalidException;

    ProductoBase findByNombreAndBajaLogica(String nombre,boolean estado);

    ProductoBase findByIdAndBajaLogica(Long id, boolean estado);

    Page<ProductoBase> findByBajaLogica(boolean bajaLogica, Pageable pageable);
}
