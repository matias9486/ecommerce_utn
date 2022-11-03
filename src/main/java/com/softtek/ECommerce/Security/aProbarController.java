package com.softtek.ECommerce.Security;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(path="")
public class aProbarController {

    @GetMapping("/publico")
    ResponseEntity<?> accesoPublico(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        String datosUsuario=auth.getName();
        String datosPermisos=auth.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
        Boolean estaAutenticado=auth.isAuthenticated();
        System.out.println("datos usuario: "+datosUsuario+ " rol: "+datosPermisos + " autenticado: " +estaAutenticado.toString());
        return new ResponseEntity<>("ingresan todos", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GESTOR')")
    @PostMapping("/gestor")
    ResponseEntity<?> accesoAdmin(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        String datosUsuario=auth.getName();
        String datosPermisos=auth.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
        Boolean estaAutenticado=auth.isAuthenticated();
        System.out.println("datos usuario: "+datosUsuario+ " rol: "+datosPermisos + " autenticado: " +estaAutenticado.toString());
        return new ResponseEntity<>("ingresan gestores", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/vendedor")
    ResponseEntity<?> accesoUsuario(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        String datosUsuario=auth.getName();
        String datosPermisos=auth.getAuthorities().stream().collect(Collectors.toList()).get(0).getAuthority();
        Boolean estaAutenticado=auth.isAuthenticated();
        System.out.println("datos usuario: "+datosUsuario+ " rol: "+datosPermisos + " autenticado: " +estaAutenticado.toString());
        return new ResponseEntity<>("ingresan vendedores", HttpStatus.OK);
    }


}
