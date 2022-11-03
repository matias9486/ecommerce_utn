
package com.softtek.ECommerce.Security;

import com.softtek.ECommerce.model.Enums.TipoRol;
import com.softtek.ECommerce.model.entity.Usuario;
import com.softtek.ECommerce.repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsuariosRepository usuariosRepositorio;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario= usuariosRepositorio.findByNombreUsuario(usernameOrEmail);
        if(usuario==null)
            throw new UsernameNotFoundException("Usuario no encontrado con ese user name o email:"+ usernameOrEmail);

        System.out.println("usuario:"+ usuario.getTipoRol().name() +" "+usuario.getNombreUsuario() + " "+usuario.getPassword());

        return  new User(usuario.getNombreUsuario(),usuario.getPassword(),mapearRoles(usuario.getTipoRol()));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(TipoRol rol){
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(rol.toString()));
        System.out.println("Role al cargar roles: "+ roles.get(0).getAuthority());
        return roles;
    }
}
