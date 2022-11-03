package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.AreaPersonalizacionDTO;
import com.softtek.ECommerce.model.dto.AreaPersonalizacionCreateDTO;
import com.softtek.ECommerce.model.entity.AreaPersonalizacion;
import com.softtek.ECommerce.model.entity.Usuario;
import com.softtek.ECommerce.repository.AreasDePersonalizacionRepository;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaPersonalizacionService {
    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AreasDePersonalizacionRepository areasDePersonalizacionRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<AreaPersonalizacionDTO> obtenerTodosFiltradosConCriteria(String nombre){
        if(nombre!=null) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<AreaPersonalizacion> criteriaQuery = cb.createQuery(AreaPersonalizacion.class);
            Root<AreaPersonalizacion> root = criteriaQuery.from(AreaPersonalizacion.class); // FROM Servicio

            criteriaQuery
                    .select(root)// SELECT * FROM areas
                    .where(cb.and(
                            cb.like(root.get("nombre"), "%" + nombre + "%")//, // nombre LIKE '%ería%'
                            //cb.between(root.get("id"), 1, 10) // id BETWEEN 1 AND 10
                    ));

            List<AreaPersonalizacion> areasFiltrada = em
                    .createQuery(criteriaQuery)
                    .getResultList();
            return areasFiltrada.stream().map(a -> mapearADTO(a)).collect(Collectors.toList());
        }
        return areasDePersonalizacionRepository.findByBajaLogicaAndNombre(false,nombre).stream().map(a->mapearADTO(a)).collect(Collectors.toList());
    }

    public Page<AreaPersonalizacionDTO> obtenerTodosFiltrado(String nombre,Pageable page){
        if(nombre!=null){
            return areasDePersonalizacionRepository.findByBajaLogicaAndNombreContains(false,nombre,page).map(a->mapearADTO(a));
        }
        else{
            return areasDePersonalizacionRepository.findByBajaLogica(false,page).map(a->mapearADTO(a));
        }
    }

    public AreaPersonalizacionDTO buscarPorId(Long Id){
        AreaPersonalizacion a= areasDePersonalizacionRepository.findByIdAndBajaLogica(Id,false);
        if(a==null)
            throw new ResourceNotFoundException("Area de Personalización","Id",Id);
        return mapearADTO(a);
    }


    public AreaPersonalizacionDTO guardarArea(Long id, AreaPersonalizacionCreateDTO areaDTO) throws ResourceInvalidException {
        //convertir a entidad
        AreaPersonalizacion area=mapearAEntidad(areaDTO);
        if(areasDePersonalizacionRepository.findByNombre(areaDTO.getNombre())!=null)
            throw new ResourceInvalidException("Area de personalización","Nombre", areaDTO.getNombre());

        //Busco gestor para asignarselo
        Usuario u=usuariosRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));

        area.setUsuario(u);
        //agrego los atributos restantes
        area.setBajaLogica(false);
        area.setFechaCreacion(LocalDate.now());
        area.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(areasDePersonalizacionRepository.save(area));
    }

    public AreaPersonalizacionDTO modificarArea(Long id, AreaPersonalizacionCreateDTO areaDTO) throws ResourceInvalidException{
        AreaPersonalizacion area= areasDePersonalizacionRepository.findByNombre(areaDTO.getNombre());
        if(area !=null)
            throw new ResourceInvalidException("Area de Personalización","Nombre", areaDTO.getNombre());


        area= areasDePersonalizacionRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Area de Personalización","Id",id));

        area.setNombre(areaDTO.getNombre());
        area.setFechaModificacion(LocalDate.now());

        area = areasDePersonalizacionRepository.save(area);
        return mapearADTO(area);
    }


    public void eliminar(Long id) {
        AreaPersonalizacion area= areasDePersonalizacionRepository.findByIdAndBajaLogica(id,false);
        if(area==null)
            throw new ResourceNotFoundException("Area de Personalización","Id",id);
        area.setBajaLogica(true);
        areasDePersonalizacionRepository.save(area);
    }


    //convierte a DTO
    private AreaPersonalizacionDTO mapearADTO(AreaPersonalizacion a){
        //AreaPersonalizacionDTO areaDTO=modelMapper.map(a,AreaPersonalizacionDTO.class);

        return AreaPersonalizacionDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .usuario(a.getUsuario().getNombre()+","+ a.getUsuario().getApellido())
                .build();
    }

    //convierte a Entidad
    private AreaPersonalizacion mapearAEntidad(AreaPersonalizacionCreateDTO a){
        AreaPersonalizacion area=modelMapper.map(a,AreaPersonalizacion.class);
        return area;
    }


}
