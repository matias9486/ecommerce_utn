package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Item,Long> {
    Item findByIdAndBajaLogica(Long id, boolean bajaLogica) throws ResourceInvalidException;

    Page<Item> findByBajaLogica(boolean bajaLogica, Pageable pageable);
}
