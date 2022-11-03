/*
package com.softtek.ECommerce.model.Projections;

import com.softtek.ECommerce.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;


@Projection(name="usuarioProyeccion", types= Usuario.class)
public interface usuarioProyeccion {
    @Value("#{target.nombre} #{target.apellido}")
    String getNombreCompleto();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.usuario}")
    String getUsuario();

    @Value("#{target.rol}")
    String getRol();
}
*/