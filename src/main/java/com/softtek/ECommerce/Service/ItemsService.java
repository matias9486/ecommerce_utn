package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.*;
import com.softtek.ECommerce.repository.AreasDePersonalizacionRepository;
import com.softtek.ECommerce.repository.CarroComprasRepository;
import com.softtek.ECommerce.repository.ItemsRepository;
import com.softtek.ECommerce.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ItemsService {
    @Autowired
    private PublicacionRepository publicaciones;

    @Autowired
    private ItemsRepository items;

    @Transactional
    public Page<ItemDTO> obtenerTodos(Pageable page){
        return items.findByBajaLogica(false,page).map(a->mapearItemADTO(a));
    }

    @Transactional
    public Item buscarPorId(Long Id){
        Item i= items.findByIdAndBajaLogica(Id,false);
        if(i==null)
            throw new ResourceNotFoundException("Item","Id",Id);
        return i;
    }

    @Transactional
    public Publicacion buscarPublicacionPorId(Long Id){
        Publicacion p= publicaciones.findByIdAndBajaLogica(Id,false);
        if(p==null)
            throw new ResourceNotFoundException("Publicación","Id",Id);
        return p;
    }

    @Transactional
    public Item guardar(Long publicacionId, ItemCreateDTO dto) throws ResourceInvalidException {
        //buscar publicacion
        Publicacion p= publicaciones.findByIdAndBajaLogica(publicacionId,false);
        if(p==null)
            throw new ResourceNotFoundException("Publicacion","Id", publicacionId);

        //creo item
        Item i= new Item();
        i.setPublicacion(p);
        i.setCantidad(dto.getCantidad());
        i.setBajaLogica(false);
        i.setFechaCreacion(LocalDate.now());
        i.setFechaModificacion(LocalDate.now());
        i=items.save(i);

        return i;
    }

    public Item guardar(ItemPublicacionDTO dto) throws ResourceInvalidException {
        //buscar publicacion
        Publicacion p= publicaciones.findByIdAndBajaLogica(dto.getPublicacionId(),false);
        if(p==null)
            throw new ResourceNotFoundException("Publicacion","Id", dto.getPublicacionId());

        //creo item
        Item i= new Item();
        i.setPublicacion(p);
        i.setCantidad(dto.getCantidad());
        i.setBajaLogica(false);
        i.setFechaCreacion(LocalDate.now());
        i.setFechaModificacion(LocalDate.now());
        i=items.save(i);

        return i;
    }

    @Transactional
    public Item editar(Long itemId, ItemPublicacionDTO editar) {
        //buscar item
        Item item=items.findByIdAndBajaLogica(itemId,false);
        if(item==null)
            throw new ResourceNotFoundException("Item","Id",itemId);
        //buscar publicacion
        Publicacion publicacion=publicaciones.findByIdAndBajaLogica(editar.getPublicacionId(),false);
        if(publicacion==null)
            throw new ResourceNotFoundException("Publicación","Id",editar.getPublicacionId());

        item.setCantidad(editar.getCantidad());
        item.setFechaModificacion(LocalDate.now());
        item.setPublicacion(publicacion);
        //guardar item
        return items.save(item);
    }

    //convierte a DTO
    public ItemDTO mapearItemADTO(Item i){
        int cantidad=i.getCantidad();
        Double precioUnitario=i.getPublicacion().getProducto().calcularPrecio();
        Double precioTotal=cantidad*precioUnitario;
        return ItemDTO.builder()
                .id(i.getId())
                .cantidad(cantidad)
                .producto(i.getPublicacion().getProducto().getNombre())
                .precioUnitario(precioUnitario)
                .precioTotal(precioTotal)
                .build();
    }

    //convierte a Entidad
    public Item mapearItemAEntidad(ItemCreateDTO dto){
        Item i=new Item();
        i.setCantidad(dto.getCantidad());
        return i;
    }

}
