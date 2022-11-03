package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.*;
import com.softtek.ECommerce.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class ProductoBaseService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductoBaseRepository productosBaseRepository;

    @Autowired
    private PosiblesPersonalizacionesRepository posiblesPersonalizacionesRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;


    public Page<ProductoBaseDTO> obtenerTodos(Pageable page){
        return productosBaseRepository.findByBajaLogica(false,page).map(a->mapearADTO(a));
    }

    public ProductoBaseDTO buscarPorId(Long Id){
        ProductoBase aux= productosBaseRepository.findByIdAndBajaLogica(Id,false);
        if(aux==null)
            throw new ResourceNotFoundException("Producto Base","Id",Id);
        return mapearADTO(aux);
    }


    public ProductoBaseDTO guardar(Long id, ProductoBaseCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        ProductoBase aux=mapearAEntidad(dto);
        if(productosBaseRepository.findByNombreAndBajaLogica(dto.getNombre(),false)!=null)
            throw new ResourceInvalidException("Producto Base","Nombre",dto.getNombre());

        //Busco gestor para asignarselo
        Usuario u=usuariosRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));

        aux.setUsuario(u);
        //agrego los atributos restantes
        aux.setBajaLogica(false);
        aux.setFechaCreacion(LocalDate.now());
        aux.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(productosBaseRepository.save(aux));
    }

    public ProductoBaseDTO modificar(Long id, ProductoBaseCreateDTO dto) throws ResourceInvalidException{
        ProductoBase p= productosBaseRepository.findByNombreAndBajaLogica(dto.getNombre(),false);
        //producto base existente
        if(p!=null)
            throw new ResourceInvalidException("Producto Base","Nombre",dto.getNombre());

        p= productosBaseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Producto Base","Id",id));

        ProductoBase aux=mapearAEntidad(dto);
        p.setNombre(aux.getNombre());
        p.setTiempoFabricacion(aux.getTiempoFabricacion());
        p.setDescripcion(aux.getDescripcion());
        p.setPrecio(aux.getPrecio());
        p.setFechaModificacion(LocalDate.now());

        p = productosBaseRepository.save(p);
        return mapearADTO(p);
    }


    public void eliminar(Long id) {
        ProductoBase eliminado= productosBaseRepository.findByIdAndBajaLogica(id,false);
        if(eliminado==null)
            throw new ResourceNotFoundException("Producto Base","Id",id);
        eliminado.setBajaLogica(true);
        productosBaseRepository.save(eliminado);
    }

    public ProductoBaseDTO agregarPosiblePersonalizacion(Long productoId, ProductoBasePosiblePersonalizacionDTO posiblePersonalizacion){
        ProductoBase aux= productosBaseRepository.findByIdAndBajaLogica(productoId,false);
        if(!productosBaseRepository.existsById(productoId))
            throw new ResourceNotFoundException("Producto Base","Id",productoId);

        PosiblePersonalizacion personalizacion=posiblesPersonalizacionesRepository.findByIdAndBajaLogica(posiblePersonalizacion.getPosiblePersonalizacionId(),false);
        if(personalizacion==null)
            throw new ResourceNotFoundException("Posible Personalizacion","Id",posiblePersonalizacion.getPosiblePersonalizacionId());

        if(aux.comprobarExistenciaPosiblePersonalizacion(posiblePersonalizacion.getPosiblePersonalizacionId())){
            throw new ResourceInvalidException("Producto Base con posible personalizacion");
        }

        aux.setFechaModificacion(LocalDate.now());
        aux.agregarPosiblePersonalizacion(personalizacion);

        //convertir a DTO
        return mapearADTO(productosBaseRepository.save(aux));
    }

    //convierte a DTO
    private ProductoBaseDTO mapearADTO(ProductoBase a){

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

    //convierte a Entidad
    private ProductoBase mapearAEntidad(ProductoBaseCreateDTO a){
        ProductoBase p =new ProductoBase();
        p.setNombre(a.getNombre());
        p.setPrecio(a.getPrecio());
        p.setDescripcion(a.getDescripcion());
        p.setTiempoFabricacion(a.getTiempoFabricacion());
        return p;
    }

    private PosiblePersonalizacionDTO mapearPosiblePersonalizacionADTO(PosiblePersonalizacion a){
        return PosiblePersonalizacionDTO.builder()
                .area(a.getArea().getNombre())
                .tipo(a.getTipo().getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }
}
