package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.PersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.PersonalizacionDTO;
import com.softtek.ECommerce.model.entity.Personalizacion;
import com.softtek.ECommerce.model.entity.Tienda;
import com.softtek.ECommerce.repository.PersonalizacionesRepository;
import com.softtek.ECommerce.repository.TiendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PersonalizacionService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonalizacionesRepository personalizaciones;

    @Autowired
    private TiendaRepository tiendas;

    public Page<PersonalizacionDTO> obtenerTodos(String nombre, Pageable page){
        if(nombre!=null){
            return personalizaciones.findByNombreContainsAndBajaLogica(nombre,false,page).map(p->mapearADTO(p));
        }
        else{
            return personalizaciones.findByBajaLogica(false,page).map(p->mapearADTO(p));
        }
    }

    public PersonalizacionDTO buscarPorId(Long Id){
        Personalizacion p= personalizaciones.findByIdAndBajaLogica(Id,false);
        if(p==null)
            throw new ResourceNotFoundException("Personalización","Id",Id);
        return mapearADTO(p);
    }


    public PersonalizacionDTO guardar(Long id, PersonalizacionCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        Personalizacion nuevo=mapearAEntidad(dto);
        if(personalizaciones.findByNombre(dto.getNombre())!=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());

        //Busco tienda para asignarselo
        Tienda t=tiendas.findById(id).orElseThrow(()->new ResourceNotFoundException("Tienda","Id",id));

        nuevo.setTienda(t);
        //agrego los atributos restantes
        nuevo.setBajaLogica(false);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(personalizaciones.save(nuevo));
    }

    public PersonalizacionDTO modificar(Long id, PersonalizacionCreateDTO dto) throws ResourceInvalidException{
        Personalizacion editar= personalizaciones.findByNombre(dto.getNombre());
        if(editar !=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());

        editar= personalizaciones.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Personalización","Id",id));

        editar.setNombre(dto.getNombre());
        editar.setFechaModificacion(LocalDate.now());

        editar = personalizaciones.save(editar);
        return mapearADTO(editar);
    }


    public void eliminar(Long id) {
        Personalizacion eliminar= personalizaciones.findByIdAndBajaLogica(id,false);
        if(eliminar==null)
            throw new ResourceNotFoundException("Personalización","Id",id);
        eliminar.setBajaLogica(true);
        personalizaciones.save(eliminar);
    }


    //convierte a DTO
    private PersonalizacionDTO mapearADTO(Personalizacion a){
        return PersonalizacionDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .contenido(a.getContenido())
                .precio(a.getPrecio())
                .build();
    }

    //convierte a Entidad
    private Personalizacion mapearAEntidad(PersonalizacionCreateDTO dto){
        Personalizacion p=modelMapper.map(dto,Personalizacion.class);
        return p;
    }
}
