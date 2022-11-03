package com.softtek.ECommerce.repository;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuario,Long> {

    Usuario findByNombreUsuario(String usuario) throws ResourceInvalidException;
    Usuario findByEmail(String email) throws ResourceInvalidException;

    List<Usuario> findByBajaLogica(boolean estado);
    Usuario findByIdAndBajaLogica(Long id, boolean estado);

    Boolean existsByNombreUsuario(String nombreUsuario);
    Boolean existsByEmail(String email);
    Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);
}
