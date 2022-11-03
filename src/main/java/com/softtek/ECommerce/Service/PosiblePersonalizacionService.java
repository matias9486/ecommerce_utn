package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.PosiblePersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.PosiblePersonalizacionDTO;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionDTO;
import com.softtek.ECommerce.model.entity.AreaPersonalizacion;
import com.softtek.ECommerce.model.entity.PosiblePersonalizacion;
import com.softtek.ECommerce.model.entity.TipoPersonalizacion;
import com.softtek.ECommerce.model.entity.Usuario;
import com.softtek.ECommerce.repository.AreasDePersonalizacionRepository;
import com.softtek.ECommerce.repository.PosiblesPersonalizacionesRepository;
import com.softtek.ECommerce.repository.TipoPersonalizacionRepository;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PosiblePersonalizacionService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PosiblesPersonalizacionesRepository posiblesPersonalizacionesRepository;

    @Autowired
    private TipoPersonalizacionRepository tipoPersonalizacionRepository;

    @Autowired
    private AreasDePersonalizacionRepository areasDePersonalizacionRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;


    public Page<PosiblePersonalizacionDTO> obtenerTodos(Pageable page){
        return posiblesPersonalizacionesRepository.findByBajaLogica(false,page).map(a->mapearADTO(a));
    }

    public PosiblePersonalizacionDTO buscarPorId(Long Id){
        PosiblePersonalizacion a= posiblesPersonalizacionesRepository.findByIdAndBajaLogica(Id,false);
        if(a==null)
            throw new ResourceNotFoundException("Posible Personalización","Id",Id);
        return mapearADTO(a);
    }


    public PosiblePersonalizacionDTO guardar(Long id, PosiblePersonalizacionCreateDTO dto) throws ResourceInvalidException {
        //convertir a entidad
        PosiblePersonalizacion personalizacion=mapearAEntidad(dto);
        if(posiblesPersonalizacionesRepository.findByAreaAndTipoAndBajaLogica(dto.getAreaId(),dto.getTipoId(),false)!=null)
            throw new ResourceInvalidException("Posible personalización");

        //Busco gestor para asignarselo
        Usuario u=usuariosRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));

        personalizacion.setUsuario(u);
        //agrego los atributos restantes
        personalizacion.setBajaLogica(false);
        personalizacion.setFechaCreacion(LocalDate.now());
        personalizacion.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(posiblesPersonalizacionesRepository.save(personalizacion));
    }

    public PosiblePersonalizacionDTO modificar(Long id, PosiblePersonalizacionCreateDTO dto) throws ResourceInvalidException{
        PosiblePersonalizacion posible= posiblesPersonalizacionesRepository.findByAreaAndTipoAndBajaLogica(dto.getAreaId(),dto.getTipoId(),false);
        //personalizacion existente
        if(posible!=null)
            throw new ResourceInvalidException("Posible personalización");

        posible= posiblesPersonalizacionesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Posible Personalización","Id",id));

        PosiblePersonalizacion aux=mapearAEntidad(dto);
        posible.setArea(aux.getArea());
        posible.setTipo(aux.getTipo());
        posible.setFechaModificacion(LocalDate.now());

        posible = posiblesPersonalizacionesRepository.save(posible);
        return mapearADTO(posible);
    }


    public void eliminar(Long id) {
        PosiblePersonalizacion posible= posiblesPersonalizacionesRepository.findByIdAndBajaLogica(id,false);
        if(posible==null)
            throw new ResourceNotFoundException("Posible Personalización","Id",id);
        posible.setBajaLogica(true);
        posiblesPersonalizacionesRepository.save(posible);
    }


    //convierte a DTO
    private PosiblePersonalizacionDTO mapearADTO(PosiblePersonalizacion a){
        return PosiblePersonalizacionDTO.builder()
                .area(a.getArea().getNombre())
                .tipo(a.getTipo().getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }

    //convierte a Entidad
    private PosiblePersonalizacion mapearAEntidad(PosiblePersonalizacionCreateDTO a){
        /*PosiblePersonalizacion posible=modelMapper.map(a,PosiblePersonalizacion.class);
        return posible;
         */
        PosiblePersonalizacion p =new PosiblePersonalizacion();
        AreaPersonalizacion area=areasDePersonalizacionRepository.findById(a.getAreaId()).get();
        TipoPersonalizacion tipo=tipoPersonalizacionRepository.findById(a.getTipoId()).get();
        p.setArea(area);
        p.setTipo(tipo);
        return p;
    }
}
