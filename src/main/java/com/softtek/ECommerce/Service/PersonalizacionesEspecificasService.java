package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.Personalizacion;
import com.softtek.ECommerce.model.entity.PersonalizacionEspecifica;
import com.softtek.ECommerce.model.entity.PosiblePersonalizacion;
import com.softtek.ECommerce.model.entity.Tienda;
import com.softtek.ECommerce.repository.PersonalizacionesEspecificasRepository;
import com.softtek.ECommerce.repository.PersonalizacionesRepository;
import com.softtek.ECommerce.repository.PosiblesPersonalizacionesRepository;
import com.softtek.ECommerce.repository.TiendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PersonalizacionesEspecificasService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PersonalizacionesRepository personalizaciones; //que pondre en la posible personalizacion

    @Autowired
    private PosiblesPersonalizacionesRepository posiblesPersonalizaciones; //area y tipo

    @Autowired
    private PersonalizacionesEspecificasRepository personalizacionesEspecificas; //personalizacion a aplicar

    @Autowired
    private TiendaRepository tiendas;

    public Page<PersonalizacionEspecificaDTO> obtenerTodos(Pageable page){
        return personalizacionesEspecificas.findByBajaLogica(false,page).map(p->mapearADTO(p));
    }

    public PersonalizacionEspecificaDTO buscarPorId(Long Id){
        PersonalizacionEspecifica p= personalizacionesEspecificas.findByIdAndBajaLogica(Id,false);
        if(p==null)
            throw new ResourceNotFoundException("Personalización Especifica","Id",Id);
        return mapearADTO(p);
    }


    public PersonalizacionEspecificaDTO guardar(Long id, PersonalizacionEspecificaCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        PersonalizacionEspecifica nuevo=mapearAEntidad(dto);
        /* comprobar que no exista personalizacion especifica
        if(personalizacionesEs.findByNombre(dto.getNombre())!=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());
        */

        //Busco tienda para asignarselo
        Tienda t=tiendas.findById(id).orElseThrow(()->new ResourceNotFoundException("Tienda","Id",id));
        //Busco posible personalizacion para asignarselo
        PosiblePersonalizacion posiblePersonalizacion=posiblesPersonalizaciones.findById(dto.getPosiblePersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Posible Personalización","Id",dto.getPosiblePersonalizacionId()));
        //Busco Personalizacion para asignarselo
        Personalizacion personalizacion=personalizaciones.findById(dto.getPersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Personalizacion","Id",dto.getPersonalizacionId()));

        nuevo.setTienda(t);
        nuevo.setPosiblePersonalizacion(posiblePersonalizacion);
        nuevo.setPersonalizacion(personalizacion);
        //agrego los atributos restantes
        nuevo.setBajaLogica(false);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(personalizacionesEspecificas.save(nuevo));
    }

    public PersonalizacionEspecificaDTO modificar(Long id, PersonalizacionEspecificaCreateDTO dto) throws ResourceInvalidException{
        /* comprobar si existe personalizacion especifia
        Personalizacion editar= personalizaciones.findByNombre(dto.getNombre());
        if(editar !=null)
            throw new ResourceInvalidException("Personalización","Nombre", dto.getNombre());
*/
        PersonalizacionEspecifica editar= personalizacionesEspecificas.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Personalización Especifica","Id",id));

        //Busco tienda para asignarselo
        //Tienda t=tiendas.findById(id).orElseThrow(()->new ResourceNotFoundException("Tienda","Id",id));
        //Busco posible personalizacion para asignarselo
        PosiblePersonalizacion posiblePersonalizacion=posiblesPersonalizaciones.findById(dto.getPosiblePersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Posible Personalización","Id",dto.getPosiblePersonalizacionId()));
        //Busco Personalizacion para asignarselo
        Personalizacion personalizacion=personalizaciones.findById(dto.getPersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Personalizacion","Id",dto.getPersonalizacionId()));

        //editar.setTienda(t);
        editar.setPosiblePersonalizacion(posiblePersonalizacion);
        editar.setPersonalizacion(personalizacion);

        editar.setFechaModificacion(LocalDate.now());
        editar = personalizacionesEspecificas.save(editar);
        return mapearADTO(editar);
    }


    public void eliminar(Long id) {
        PersonalizacionEspecifica eliminar= personalizacionesEspecificas.findByIdAndBajaLogica(id,false);
        if(eliminar==null)
            throw new ResourceNotFoundException("Personalización Especifica","Id",id);
        eliminar.setBajaLogica(true);
        personalizacionesEspecificas.save(eliminar);
    }


    //convierte a DTO
    private PersonalizacionEspecificaDTO mapearADTO(PersonalizacionEspecifica a){
        return PersonalizacionEspecificaDTO.builder()
                .id(a.getId())
                .personalizacion(mapearPersonalizacionADTO(a.getPersonalizacion()))
                .posiblePersonalizacion(mapearPosiblePersonalizacionADTO(a.getPosiblePersonalizacion()))
                .build();
    }

    //convierte a Entidad
    private PersonalizacionEspecifica mapearAEntidad(PersonalizacionEspecificaCreateDTO dto){
        PersonalizacionEspecifica especifica=new PersonalizacionEspecifica();
        //Busco posible personalizacion para asignarselo
        PosiblePersonalizacion posiblePersonalizacion=posiblesPersonalizaciones.findById(dto.getPosiblePersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Posible Personalización","Id",dto.getPosiblePersonalizacionId()));
        //Busco Personalizacion para asignarselo
        Personalizacion personalizacion=personalizaciones.findById(dto.getPersonalizacionId()).orElseThrow(()->new ResourceNotFoundException("Personalizacion","Id",dto.getPersonalizacionId()));

        especifica.setPersonalizacion(personalizacion);
        especifica.setPosiblePersonalizacion(posiblePersonalizacion);
        return especifica;
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
}
