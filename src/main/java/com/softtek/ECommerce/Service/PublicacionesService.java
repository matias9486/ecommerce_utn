package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.Enums.EstadoPublicacion;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.*;
import com.softtek.ECommerce.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.stream.Collectors;

@Service
public class PublicacionesService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicaciones;

    @Autowired
    private TiendaRepository tiendas;

    @Autowired
    private ProductoBaseRepository productosBases;

    @Autowired
    private ProductosPersonalizadosRepository productosPersonalizados;

    public Page<PublicacionDTO> obtenerTodos(Pageable page){
        return publicaciones.findByBajaLogica(false,page).map(p->mapearPublicacionADTO(p));
    }

    public PublicacionDTO buscarPorId(Long Id){
        Publicacion p= publicaciones.findByIdAndBajaLogica(Id,false);
        if(p==null)
            throw new ResourceNotFoundException("Publicacion","Id",Id);
        return mapearPublicacionADTO(p);
    }

    @Transactional
    public PublicacionDTO guardar(Long id, PublicacionCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        Publicacion nuevo=mapearAEntidad(dto);

        //Busco tienda para asignarselo
        Tienda t=tiendas.findById(id).orElseThrow(()->new ResourceNotFoundException("Tienda","Id",id));
        //Busco producto base para asignarselo
        ProductoPersonalizado producto=productosPersonalizados.findById(dto.getProductoId()).orElseThrow(()->new ResourceNotFoundException("Producto Personalizado","Id",dto.getProductoId()));

        nuevo.setTienda(t);
        nuevo.setDescripcion(dto.getDescripcion());
        nuevo.setProducto(producto);
        //agrego los atributos restantes
        nuevo.setBajaLogica(false);
        nuevo.setEstado(EstadoPublicacion.Activa);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setFechaModificacion(LocalDate.now());
        nuevo.setFechaPublicacion(LocalDate.now());
        nuevo.setFecha_cambio_estado(LocalDate.now());

        //convertir a DTO
        return mapearPublicacionADTO(publicaciones.save(nuevo));
    }



    public PublicacionDTO modificar(Long id, PublicacionCreateDTO dto) throws ResourceInvalidException{
        Publicacion editar= publicaciones.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Publicacion","Id",id));

        //Busco producto personalizado para asignarselo
        ProductoPersonalizado producto=productosPersonalizados.findById(dto.getProductoId()).orElseThrow(()->new ResourceNotFoundException("Producto Base","Id",dto.getProductoId()));

        editar.setDescripcion(dto.getDescripcion());
        editar.setProducto(producto);
        //agrego los atributos restantes
        editar.setFechaModificacion(LocalDate.now());
        //convertir a DTO
        return mapearPublicacionADTO(publicaciones.save(editar));
    }


    public void eliminar(Long id) {
        Publicacion eliminar= publicaciones.findByIdAndBajaLogica(id,false);
        if(eliminar==null)
            throw new ResourceNotFoundException("Publicacion","Id",id);
        eliminar.setBajaLogica(true);
        eliminar.setEstado(EstadoPublicacion.Cancelada);
        publicaciones.save(eliminar);
    }

    public PublicacionDTO cambiarEstado(Long publicacionId,EstadoPublicacionDTO estado){
        Publicacion publicacion= publicaciones.findById(publicacionId)
                .orElseThrow(()->new ResourceNotFoundException("Publicacion","Id",publicacionId));

        boolean estadoValido=false;
        for (EstadoPublicacion e: EstadoPublicacion.values()) {
            if(e==estado.getEstado())
                estadoValido=true;
        }
        if(estadoValido==false)
            throw new ResourceNotFoundException("Estado Publicación",estado.getEstado().name());

        publicacion.setEstado(estado.getEstado());
        publicacion.setFecha_cambio_estado(LocalDate.now());
        publicacion.setFechaModificacion(LocalDate.now());
        return mapearPublicacionADTO(publicaciones.save(publicacion));
    }

    //convierte a DTO
    private PublicacionDTO mapearPublicacionADTO(Publicacion a){
        return PublicacionDTO.builder()
                .id(a.getId())
                .descripcion(a.getDescripcion())
                .fechaCreacion(a.getFechaCreacion())
                .estadoPublicacion(a.getEstado())
                .fechaCambioEstado(a.getFecha_cambio_estado())
                .producto(mapearProductoPersonalizadoADTO(a.getProducto()))
                .build();
    }

    //convierte a Entidad
    private Publicacion mapearAEntidad(PublicacionCreateDTO dto){
        Publicacion publicacion=new Publicacion();
        //Busco producto base para asignarselo
        ProductoPersonalizado producto=productosPersonalizados.findById(dto.getProductoId()).orElseThrow(()->new ResourceNotFoundException("Producto Personalizado","Id",dto.getProductoId()));

        publicacion.setProducto(producto);
        publicacion.setDescripcion(dto.getDescripcion());
        return publicacion;
    }

    private ProductoPersonalizadoDTO mapearProductoPersonalizadoADTO(ProductoPersonalizado a){
        return ProductoPersonalizadoDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .descripcion(a.getDescripcion())
                .precio(a.calcularPrecio())
                .productoBase(mapearProductoBaseADTO(a.getProductoOrigen()))
                .personalizacionesAplicadas(a.getPersonalizacionesAplicadas()
                        .stream()
                        .map(p-> mapearPersonalizacionEspecificaADTO(p))
                        .collect(Collectors.toList()))
                .build();
    }

    private ProductoBaseDTO mapearProductoBaseADTO(ProductoBase a){

        return ProductoBaseDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .descripcion(a.getDescripcion())
                .precio(a.getPrecio())
                .tiempoFabricacion(a.getTiempoFabricacion())
                .personalizaciones(a.getPosiblesPersonalizaciones()
                        .stream()
                        .map(p->mapearPosiblePersonalizacionADTO(p))
                        .collect(Collectors.toList()))
                .build();
    }

    private PosiblePersonalizacionDTO mapearPosiblePersonalizacionADTO(PosiblePersonalizacion a){
        return PosiblePersonalizacionDTO.builder()
                .area(a.getArea().getNombre())
                .tipo(a.getTipo().getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }

    private PersonalizacionEspecificaDTO mapearPersonalizacionEspecificaADTO(PersonalizacionEspecifica a){
        return PersonalizacionEspecificaDTO.builder()
                .id(a.getId())
                .personalizacion(mapearPersonalizacionADTO(a.getPersonalizacion()))
                .posiblePersonalizacion(mapearPosiblePersonalizacionADTO(a.getPosiblePersonalizacion()))
                .build();
    }

    private PersonalizacionDTO mapearPersonalizacionADTO(Personalizacion a){
        return PersonalizacionDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .contenido(a.getContenido())
                .precio(a.getPrecio())
                .build();
    }

}
