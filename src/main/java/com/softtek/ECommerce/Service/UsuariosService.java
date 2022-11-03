package com.softtek.ECommerce.Service;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Exceptions.ResourceNotFoundException;
import com.softtek.ECommerce.model.dto.UsuarioDTO;
import com.softtek.ECommerce.model.entity.Usuario;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UsuariosService {

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuariosRepository UsuariosRepository;

    public List<UsuarioDTO> obtenerTodos(){
        return (List<UsuarioDTO>) UsuariosRepository.findByBajaLogica(false).stream().map(u->mapearADTO(u)).collect(Collectors.toList());
    }

    public UsuarioDTO buscarPorId(Long usuarioId){
        //Usuario u=repositorioUsuariosCRUD.findById(usuarioId).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",usuarioId));
        Usuario u= UsuariosRepository.findByIdAndBajaLogica(usuarioId,false);
        if(u==null)
            throw new ResourceNotFoundException("Usuario","Id",usuarioId);
        return mapearADTO(u);
    }

    public Boolean existePorNombreUsuario(String nombreUsuario){
        if(UsuariosRepository.existsByNombreUsuario(nombreUsuario))
            throw new ResourceInvalidException("Usuario","Nombre de usuario",nombreUsuario);
        return true;
    }

    public Boolean existePorEmail(String email){
        if(UsuariosRepository.existsByEmail(email))
            throw new ResourceInvalidException("Usuario","Email",email);
        return true;
    }

    //public Usuario guardarUsuario(Usuario usuario) throws UsuarioInvalidoException {
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) throws ResourceInvalidException {
        //convertir a entidad
        Usuario usuario=mapearAEntidad(usuarioDTO);

        if(UsuariosRepository.findByNombreUsuario(usuario.getNombreUsuario())!=null)
            throw new ResourceInvalidException("Usuario","Nick", usuarioDTO.getNombreUsuario());

        if(UsuariosRepository.findByEmail(usuario.getEmail())!=null)
            throw new ResourceInvalidException("Usuario","Email", usuarioDTO.getEmail());

        //usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setPassword(usuario.getPassword());
        //agrego los atributos del usuario
        usuario.setBajaLogica(false);
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setFechaModificacion(LocalDate.now());

        //convertir a DTO
        return mapearADTO(UsuariosRepository.save(usuario));
    }

    public UsuarioDTO modificarUsuario(UsuarioDTO usuarioDTO) throws ResourceInvalidException {
        Usuario user= UsuariosRepository.findByNombreUsuario(usuarioDTO.getNombreUsuario());
        //verifico que nick y mail esten disponibles
        if(user !=null && user.getId()!=usuarioDTO.getId())
            throw new ResourceInvalidException("Usuario","Nick", usuarioDTO.getNombreUsuario());

        user= UsuariosRepository.findByEmail(usuarioDTO.getEmail());
        if(user!=null && user.getId()!=usuarioDTO.getId())
            throw new ResourceInvalidException("Usuario","Email", usuarioDTO.getEmail());


        user= UsuariosRepository.findById(usuarioDTO.getId())
                        .orElseThrow(()->new ResourceNotFoundException("Usuario","Id",usuarioDTO.getId()));

        user.setNombre(usuarioDTO.getNombre());
        user.setApellido(usuarioDTO.getApellido());
        user.setTipoRol(usuarioDTO.getTipoRol());
        user.setNombreUsuario(usuarioDTO.getNombreUsuario());
        user.setEmail(usuarioDTO.getEmail());
        //user.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        user.setPassword(usuarioDTO.getPassword());
        user.setFechaModificacion(LocalDate.now());

        user = UsuariosRepository.save(user);
        return mapearADTO(user);
    }

    public boolean comprobarExistencia(Long usuarioID){
        return UsuariosRepository.existsById(usuarioID);
    }

    public void eliminar(Long id) {
        //Usuario user=repositorioUsuariosCRUD.findById(id).orElseThrow(()->new ResourceNotFoundException("Usuario","Id",id));
        Usuario u= UsuariosRepository.findByIdAndBajaLogica(id,false);
        if(u==null)
            throw new ResourceNotFoundException("Usuario","Id",id);
        u.setBajaLogica(true);
        UsuariosRepository.save(u);
    }

    //mapeo usando modelMapper
    //convierte a DTO
    private UsuarioDTO mapearADTO(Usuario u){
        UsuarioDTO usuarioDTO=modelMapper.map(u,UsuarioDTO.class);
        return usuarioDTO;
    }

    //convierte a Entidad
    private Usuario mapearAEntidad(UsuarioDTO u){
        Usuario usuario=modelMapper.map(u,Usuario.class);
        return usuario;
    }

    //usando builder de Lombok
    private UsuarioDTO mapearADTOLombok(Usuario u){
        return UsuarioDTO.builder()
                .id(u.getId())
                .nombreUsuario(u.getNombreUsuario())
                .email(u.getEmail())
                .password(u.getPassword())
                .apellido(u.getApellido())
                .nombre(u.getNombre())
                .tipoRol(u.getTipoRol())
                .build();
    }
}
