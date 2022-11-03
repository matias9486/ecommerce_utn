package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ErrorException;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class ProductosPersonalizadosService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonalizacionesEspecificasRepository personalizacionesEspecificas; //personalizacion a aplicar

    @Autowired
    private TiendaRepository tiendas;

    @Autowired
    private ProductoBaseRepository productosBases;

    @Autowired
    private ProductosPersonalizadosRepository productosPersonalizados;

    @Transactional
    public Page<ProductoPersonalizadoDTO> obtenerTodos(Pageable page){
        return productosPersonalizados.findByBajaLogica(false,page).map(p->mapearADTO(p));
    }

    @Transactional
    public ProductoPersonalizadoDTO buscarPorId(Long Id){
        ProductoPersonalizado p= productosPersonalizados.findByIdAndBajaLogica(Id,false);
        if(p==null)
            throw new ResourceNotFoundException("Producto Personalizado","Id",Id);
        return mapearADTO(p);
    }

    @Transactional
    public ProductoPersonalizadoDTO guardar(Long id, ProductoPersonalizadoCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        ProductoPersonalizado nuevo=mapearAEntidad(dto);
        /* comprobar que no exista personalizacion especifica
        if(personalizacionesEs.findByNombre(dto.getNombre())!=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());
        */

        //Busco tienda para asignarselo
        Tienda t=tiendas.findById(id).orElseThrow(()->new ResourceNotFoundException("Tienda","Id",id));
        //Busco producto base para asignarselo
        ProductoBase base=productosBases.findById(dto.getProductoBaseId()).orElseThrow(()->new ResourceNotFoundException("Producto Base","Id",dto.getProductoBaseId()));

        nuevo.setTienda(t);
        nuevo.setNombre(dto.getNombre());
        nuevo.setDescripcion(dto.getDescripcion());
        nuevo.setProductoOrigen(base);
        //agrego los atributos restantes
        nuevo.setBajaLogica(false);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(productosPersonalizados.save(nuevo));
    }

    @Transactional
    public ProductoPersonalizadoDTO agregarPersonalizacionEspecifica(Long id, ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO nueva) throws ResourceInvalidException {
        ProductoPersonalizado producto= productosPersonalizados.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Producto personalizado","Id",id));

        PersonalizacionEspecifica personalizacionEspecifica= personalizacionesEspecificas.findByIdAndBajaLogica(nueva.getPersonalizacionEspecificaId(),false);
        if(personalizacionEspecifica==null)
            throw new ResourceNotFoundException("Personalizacion Especifica","Id",id);

        //validar que personalizacion no este agregada
        if(producto.existePersonalizacionEspecifica(personalizacionEspecifica)==true)
            throw new ResourceInvalidException("Personalizacion Especifica","Id",personalizacionEspecifica.getId().toString());

        //validar que personalizacion este en el producto base
        if(producto.validarPersonalizacionEspecificaDeProductoBase(personalizacionEspecifica)==true)
            producto.agregarPersonalizacionAplicada(personalizacionEspecifica);
        else
            throw new ErrorException("No se puede aplicar esa personalizacion en este producto");


        ProductoPersonalizado aux=productosPersonalizados.save(producto);
        return mapearADTO(aux);
    }

    @Transactional
    public ProductoPersonalizadoDTO eliminarPersonalizacionEspecifica(Long productoId, Long personalizacionId) throws ResourceInvalidException {
        ProductoPersonalizado producto= productosPersonalizados.findById(productoId)
                .orElseThrow(()->new ResourceNotFoundException("Producto personalizado","Id",productoId));

        PersonalizacionEspecifica personalizacionEspecifica= personalizacionesEspecificas.findByIdAndBajaLogica(personalizacionId,false);
        if(personalizacionEspecifica==null)
            throw new ResourceNotFoundException("Personalizacion Especifica","Id", personalizacionId);

        //validar que personalizacion no este agregada
        if(producto.existePersonalizacionEspecifica(personalizacionEspecifica)==true)
            producto.eliminarPersonalizacionAplicada(personalizacionEspecifica);
        else
            throw new ResourceInvalidException("Personalizacion Especifica","Id",personalizacionEspecifica.getId().toString());

        return mapearADTO(productosPersonalizados.save(producto));
    }

    @Transactional
    public ProductoPersonalizadoDTO modificar(Long id, ProductoPersonalizadoCreateDTO dto) throws ResourceInvalidException{
        /* comprobar si existe personalizacion especifia
        Personalizacion editar= personalizaciones.findByNombre(dto.getNombre());
        if(editar !=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());
*/
        ProductoPersonalizado editar= productosPersonalizados.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Producto personalizado","Id",id));

        //Busco producto base para asignarselo
        ProductoBase base=productosBases.findById(dto.getProductoBaseId()).orElseThrow(()->new ResourceNotFoundException("Producto Base","Id",dto.getProductoBaseId()));

        //editar.setTienda(t);
        editar.setProductoOrigen(base);
        editar.setNombre(dto.getNombre());
        editar.setDescripcion(dto.getDescripcion());

        editar.setFechaModificacion(LocalDate.now());
        editar = productosPersonalizados.save(editar);
        return mapearADTO(editar);
    }


    @Transactional
    public void eliminar(Long id) {
        ProductoPersonalizado eliminar= productosPersonalizados.findByIdAndBajaLogica(id,false);
        if(eliminar==null)
            throw new ResourceNotFoundException("Producto Personalizado","Id",id);
        eliminar.setBajaLogica(true);
        productosPersonalizados.save(eliminar);
    }


    @Transactional
    //convierte a DTO
    private ProductoPersonalizadoDTO mapearADTO(ProductoPersonalizado a){
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

    @Transactional
    //convierte a Entidad
    private ProductoPersonalizado mapearAEntidad(ProductoPersonalizadoCreateDTO dto){
        ProductoPersonalizado personalizado=new ProductoPersonalizado();
        //Busco producto base para asignarselo
        ProductoBase base=productosBases.findById(dto.getProductoBaseId()).orElseThrow(()->new ResourceNotFoundException("Producto Base","Id",dto.getProductoBaseId()));

        personalizado.setProductoOrigen(base);
        personalizado.setNombre(dto.getNombre());
        personalizado.setDescripcion(dto.getDescripcion());
        return personalizado;
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

    private PosiblePersonalizacionDTO mapearPosiblePersonalizacionADTO(PosiblePersonalizacion a){
        return PosiblePersonalizacionDTO.builder()
                .area(a.getArea().getNombre())
                .tipo(a.getTipo().getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
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
