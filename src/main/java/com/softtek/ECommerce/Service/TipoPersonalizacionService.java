package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionDTO;
import com.softtek.ECommerce.model.entity.TipoPersonalizacion;
import com.softtek.ECommerce.model.entity.Usuario;
import com.softtek.ECommerce.repository.TipoPersonalizacionRepository;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TipoPersonalizacionService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TipoPersonalizacionRepository tipoPersonalizacionRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public Page<TipoPersonalizacionDTO> obtenerTodosFiltrado(String nombre, Pageable page){
        if(nombre!=null){
            return tipoPersonalizacionRepository.findByBajaLogicaAndNombreContains(false,nombre,page).map(a->mapearADTO(a));
        }
        else{
            return tipoPersonalizacionRepository.findByBajaLogica(false,page).map(a->mapearADTO(a));
        }
    }

    public TipoPersonalizacionDTO buscarPorId(Long Id){
        TipoPersonalizacion a= tipoPersonalizacionRepository.findByIdAndBajaLogica(Id,false);
        if(a==null)
            throw new ResourceNotFoundException("Tipo de Personalización","Id",Id);
        return mapearADTO(a);
    }


    public TipoPersonalizacionDTO guardarTipo(Long id, TipoPersonalizacionCreateDTO tipoDTO) throws ResourceInvalidException {
        //convertir a entidad
        TipoPersonalizacion tipo=mapearAEntidad(tipoDTO);
        if(tipoPersonalizacionRepository.findByNombre(tipoDTO.getNombre())!=null)
            throw new ResourceInvalidException("Tipo de personalización","Nombre", tipoDTO.getNombre());

        //Busco gestor para asignarselo
        Usuario u=usuariosRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));

        tipo.setUsuario(u);
        //agrego los atributos restantes
        tipo.setBajaLogica(false);
        tipo.setFechaCreacion(LocalDate.now());
        tipo.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(tipoPersonalizacionRepository.save(tipo));
    }

    public TipoPersonalizacionDTO modificarTipo(Long id, TipoPersonalizacionCreateDTO tipoDTO) throws ResourceInvalidException{
        TipoPersonalizacion tipo= tipoPersonalizacionRepository.findByNombre(tipoDTO.getNombre());
        if(tipo !=null)
            throw new ResourceInvalidException("Tipo de Personalización","Nombre", tipoDTO.getNombre());


        tipo= tipoPersonalizacionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Tipo de Personalización","Id",id));

        tipo.setNombre(tipoDTO.getNombre());
        tipo.setFechaModificacion(LocalDate.now());

        tipo = tipoPersonalizacionRepository.save(tipo);
        return mapearADTO(tipo);
    }


    public void eliminar(Long id) {
        TipoPersonalizacion tipo= tipoPersonalizacionRepository.findByIdAndBajaLogica(id,false);
        if(tipo==null)
            throw new ResourceNotFoundException("Tipo de Personalización","Id",id);
        tipo.setBajaLogica(true);
        tipoPersonalizacionRepository.save(tipo);
    }


    //convierte a DTO
    private TipoPersonalizacionDTO mapearADTO(TipoPersonalizacion a){
        return TipoPersonalizacionDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }

    //convierte a Entidad
    private TipoPersonalizacion mapearAEntidad(TipoPersonalizacionCreateDTO a){
        TipoPersonalizacion tipo=modelMapper.map(a,TipoPersonalizacion.class);
        return tipo;
    }

}
