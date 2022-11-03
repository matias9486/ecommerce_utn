package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ErrorException;
import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.adapters.AdapterGeneradorFactura;
import com.softtek.ECommerce.model.Enums.EstadoCompra;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.*;
import com.softtek.ECommerce.repository.CarroComprasRepository;
import com.softtek.ECommerce.repository.CompradorRepository;
import com.softtek.ECommerce.repository.ComprasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroComprasService {
    @Autowired
    private AdapterGeneradorFactura generadorFactura;
    @Autowired
    private CarroComprasRepository carrosCompras;

    @Autowired
    private CompradorRepository compradores;

    @Autowired
    private ComprasRepository compras;

    @Autowired
    private ItemsService items;

    public Page<CarroCompraDTO> obtenerTodos(Pageable page){
        return carrosCompras.findByBajaLogica(false,page).map(a->mapearCarroADTO(a));
    }

    public CarroCompraDTO buscarPorId(Long Id){
        CarroCompras c= carrosCompras.findByIdAndBajaLogica(Id,false);
        if(c==null)
            throw new ResourceNotFoundException("Carro Compras","Id",Id);
        return mapearCarroADTO(c);
    }

    //crear carrito vacio
    @Transactional
    public CarroCompraDTO crearCarroConItem(Item item) throws ResourceInvalidException {
        CarroCompras nuevo= new CarroCompras();
        nuevo.setBajaLogica(false);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setFechaModificacion(LocalDate.now());
        nuevo.agregarItemACarro(item);
        //convertir a DTO
        return mapearCarroADTO(carrosCompras.save(nuevo));
    }

    //agregar item a carrito con items
    @Transactional
    public CarroCompraDTO agregarItemACarro(Long carroId,ItemPublicacionDTO item){
        Item i;
        //buscar carroId
        CarroCompras carro= carrosCompras.findByIdAndBajaLogica(carroId,false);
        if(carro==null)
            throw new ResourceNotFoundException("Carro Compras","Id",carroId);

        Publicacion p= items.buscarPublicacionPorId(item.getPublicacionId());
        System.out.println("publicacion ->tienda id: "+ p.getTienda().getId()+ " carro->tienda id primer elemento: " + carro.getItems().get(0).getPublicacion().getTienda().getId());
        if(!carro.comprobarMismaTienda(p))
            throw  new ErrorException("El producto agregado no pertenece a la misma tienda.");
        else{
            //guardar item
            i= items.guardar(item);
            carro.agregarItemACarro(i);
        }
        return  mapearCarroADTO(carrosCompras.save(carro));
    }

    @Transactional
    public Object generarFactura(Long compraId){
        //buscar compraId
        Compra compra= compras.findByIdAndBajaLogica(compraId,false);
        if(compra==null)
            throw new ResourceNotFoundException("Compra","Id",compraId);

        if(compra.getEstado()!=EstadoCompra.PAGADA)
            throw new ErrorException("La compra aun no esta paga");
        return compra.generarFactura(mapearCompraADTO(compra),generadorFactura);
    }

    @Transactional
    public CompraDTO confirmarCompra(Long carroId, CompraCreateDTO compraCreateDTO){
        //buscar carroId
        CarroCompras carro= carrosCompras.findByIdAndBajaLogica(carroId,false);
        if(carro==null)
            throw new ResourceNotFoundException("Carro Compras","Id",carroId);
        //crear comprador
        Comprador comprador=new Comprador();
        comprador.setApellido(compraCreateDTO.getApellido());
        comprador.setNombre(compraCreateDTO.getNombre());
        comprador.setDireccion(compraCreateDTO.getDireccion());
        comprador.setTelefono(compraCreateDTO.getTelefono());
        comprador.setFechaCreacion(LocalDate.now());
        comprador=compradores.save(comprador);

        //crear objeto compra
        Compra compra=new Compra();
        compra.setCarroCompras(carro);

        compra.setComprador(comprador);
        compra.setBajaLogica(false);
        compra.setEstado(EstadoCompra.PENDIENTE);
        compra.setFechaCreacion(LocalDate.now());
        compra.setMedioDePago(compraCreateDTO.getMedioPago());
        compra= compras.save(compra);
        //convertir a compraDTO
        return mapearCompraADTO(compra);
    }

    public CompraDTO confirmarPago(Long compraId){
        //buscar carroId
        Compra compra= compras.findByIdAndBajaLogica(compraId,false);
        if(compra==null)
            throw new ResourceNotFoundException("Compra","Id",compraId);

        compra.setFechaModificacion(LocalDate.now());
        compra.setEstado(EstadoCompra.PAGADA);
        compra= compras.save(compra);
        //convertir a compraDTO
        return mapearCompraADTO(compra);
    }

    //DTOs
    private CarroCompraDTO mapearCarroADTO(CarroCompras carro){
        return CarroCompraDTO.builder()
                .id(carro.getId())
                .items(carro.getItems().stream().map(i->mapearItemADTO(i)).collect(Collectors.toList()))
                .build();
    }

    private ItemDTO mapearItemADTO(Item i){
        return ItemDTO.builder()
                .id(i.getId())
                .cantidad(i.getCantidad())
                .producto(i.getPublicacion().getProducto().getDescripcion())
                .precioUnitario(i.calcularPrecioUnitario())
                .precioTotal(i.calcularTotal())
                .build();
    }

    private Item mapearItemAEntidad(ItemCreateDTO dto){
        Item i=new Item();
        i.setCantidad(dto.getCantidad());
        return i;
    }

    private CompradorDTO mapearCompradorADTO(Comprador comprador){
        return CompradorDTO.builder()
                .id(comprador.getId())
                .apellido(comprador.getApellido())
                .nombre(comprador.getNombre())
                .direccion(comprador.getDireccion())
                .telefono(comprador.getTelefono())
                .build();
    }

    private CompraDTO mapearCompraADTO(Compra compra){
        return CompraDTO.builder()
                .id(compra.getId())
                .fecha(compra.getFechaCreacion())
                .comprador(mapearCompradorADTO(compra.getComprador()))
                .estado(compra.getEstado().name())
                .medioPago(compra.getMedioDePago().name())
                .items(compra.getCarroCompras().getItems().stream().map(i->mapearItemADTO(i)).collect(Collectors.toList()))
                .total(compra.getCarroCompras().calcularTotalCompra())
                .build();
    }


}
