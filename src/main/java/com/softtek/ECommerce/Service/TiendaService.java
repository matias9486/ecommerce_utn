package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.Enums.MediosDePago;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.*;
import com.softtek.ECommerce.repository.PosiblesPersonalizacionesRepository;
import com.softtek.ECommerce.repository.ProductoBaseRepository;
import com.softtek.ECommerce.repository.TiendaRepository;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TiendaService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductoBaseRepository productosBaseRepository;

    @Autowired
    private PosiblesPersonalizacionesRepository posiblesPersonalizacionesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TiendaRepository tiendasRepository;


    public Page<TiendaDTO> obtenerTodos(Pageable page){
        return tiendasRepository.findByBajaLogica(false,page).map(a->mapearADTO(a));
    }

    public TiendaDTO buscarPorId(Long Id){
        Tienda aux= tiendasRepository.findByIdAndBajaLogica(Id,false);
        if(aux==null)
            throw new ResourceNotFoundException("Tienda","Id",Id);
        return mapearADTO(aux);
    }


    public TiendaDTO guardar(Long id, TiendaCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        Tienda aux=mapearAEntidad(dto);
        if(tiendasRepository.findByNombreAndBajaLogica(dto.getNombre(),false)!=null)
            throw new ResourceInvalidException("Tienda","Nombre",dto.getNombre());

        //Busco vendedor para asignarselo
        Usuario u=usuariosRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));

        aux.setVendedor(u);
        //agrego los atributos restantes
        aux.setBajaLogica(false);
        aux.setFechaCreacion(LocalDate.now());
        aux.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(tiendasRepository.save(aux));
    }

    public TiendaDTO modificar(Long id, TiendaCreateDTO dto) throws ResourceInvalidException{
        Tienda t= tiendasRepository.findByNombreAndBajaLogica(dto.getNombre(),false);
        //tienda nombre existente
        if(t!=null)
            throw new ResourceInvalidException("Tienda","Nombre",dto.getNombre());

        t= tiendasRepository.findByIdAndBajaLogica(id,false);
        if(t==null)
            throw new ResourceNotFoundException("Tienda","Id",id);

        t.setNombre(dto.getNombre());
        t.setFechaModificacion(LocalDate.now());

        return mapearADTO(tiendasRepository.save(t));
    }


    public void eliminar(Long id) {
        Tienda eliminado= tiendasRepository.findByIdAndBajaLogica(id,false);
        if(eliminado==null)
            throw new ResourceNotFoundException("Tienda","Id",id);
        eliminado.setBajaLogica(true);
        tiendasRepository.save(eliminado);
    }

@Transactional
    public TiendaDTO agregarMedioPago(Long tiendaId, MedioPagoCreateDTO medio){
        Tienda aux= tiendasRepository.findByIdAndBajaLogica(tiendaId,false);
        if(aux==null)
            throw new ResourceNotFoundException("Tienda","Id",tiendaId);

        //ver que no repita medio de pago
        if(aux.existeMedioPago(medio.getMedioPago()))
            throw new ResourceInvalidException("Medio de pago");

        //ver que sea valido el medio de pago
        boolean medioPagoValido=false;
        for (MediosDePago m: MediosDePago.values()) {
            if(medio.getMedioPago()==m)
                medioPagoValido=true;
        }
        if(!medioPagoValido)
            throw new ResourceNotFoundException("Medio de Pago", medio.getMedioPago().name());

        aux.setFechaModificacion(LocalDate.now());
        aux.agregarMedioPago(medio.getMedioPago());
        //convertir a DTO
        return mapearADTO(tiendasRepository.save(aux));
    }

    public TiendaDTO eliminarMedioPago(Long tiendaId, Long medio){
        Tienda aux= tiendasRepository.findByIdAndBajaLogica(tiendaId,false);
        if(aux==null)
            throw new ResourceNotFoundException("Tienda","Id",tiendaId);

        //ver que sea valido el medio de pago
        boolean medioPagoValido=false;
        for (MediosDePago m: MediosDePago.values()) {
            if(medio==m.ordinal())
                medioPagoValido=true;
        }
        if(!medioPagoValido)
            throw new ResourceNotFoundException("Tienda","Medio de Pago", medio);

        aux.setFechaModificacion(LocalDate.now());
        aux.eliminarMedioPago(medio);
        //convertir a DTO
        return mapearADTO(tiendasRepository.save(aux));
    }


    //convierte a DTO
    private TiendaDTO mapearADTO(Tienda a){

        return TiendaDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .vendedor(a.getVendedor().getNombre()+" "+a.getVendedor().getApellido())
                .mediosPago(a.getMediosDePago())
                .productos(a.getProductos().stream().map(p-> mapearProdcutoPersonalizadoADTO(p)).collect(Collectors.toList()))
                .build();
    }

    //convierte a Entidad
    private Tienda mapearAEntidad(TiendaCreateDTO a){
        Tienda t=new Tienda();
        t.setNombre(a.getNombre());
        return t;
    }

    private PosiblePersonalizacionDTO mapearPosiblePersonalizacionADTO(PosiblePersonalizacion a){
        return PosiblePersonalizacionDTO.builder()
                .area(a.getArea().getNombre())
                .tipo(a.getTipo().getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }

    private ProductoPersonalizadoDTO mapearProdcutoPersonalizadoADTO(ProductoPersonalizado a){
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

    //convierte a DTO
    private PersonalizacionDTO mapearPersonalizacionADTO(Personalizacion a){
        return PersonalizacionDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .contenido(a.getContenido())
                .precio(a.getPrecio())
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

    private PersonalizacionEspecificaDTO mapearPersonalizacionEspecificaADTO(PersonalizacionEspecifica a){
        return PersonalizacionEspecificaDTO.builder()
                .id(a.getId())
                .personalizacion(mapearPersonalizacionADTO(a.getPersonalizacion()))
                .posiblePersonalizacion(mapearPosiblePersonalizacionADTO(a.getPosiblePersonalizacion()))
                .build();
    }
}
