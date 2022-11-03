package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.UsuariosService;
import com.softtek.ECommerce.model.dto.TiendaDTO;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionDTO;
import com.softtek.ECommerce.model.dto.UsuarioDTO;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path="/usuarios")
@Api(value = "Usuarios", description = "Recurso accedido por gestores y vendedores para realizar operaciones sobre usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuarios;

    @ApiOperation(value = "Obtener los usuarios creados",notes = "Un usuario es quien accede al sistema. Puede tener rol de Gestor, vendedor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = UsuarioDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("")
    public ResponseEntity<?> obtenerUsuarios(){
        List<UsuarioDTO> result=usuarios.obtenerTodos();
        if(result.isEmpty()){
            return ResponseEntity.ok("No se ingresaron usuarios.");
        }else{
            return ResponseEntity.ok(result);
        }
    }

    @ApiOperation(value = "Obtener usuario por id",notes = "Un usuario es quien accede al sistema. Puede tener rol de Gestor, vendedor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = UsuarioDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@ApiParam(value = "Id del usuario a buscar",required = true,type = "int",example = "1")
                                                    @PathVariable("id") Long id){
        return  new ResponseEntity(usuarios.buscarPorId(id),HttpStatus.OK);
    }


    @ApiOperation(value = "Crear un usuario",notes = "Un usuario es quien accede al sistema. Puede tener rol de Gestor, vendedor.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = UsuarioDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("")
    public ResponseEntity<?> guardarUsuario(@ApiParam(value = "datos correspondientes a un usario a crear",required = true,type = "UsuarioDTO.class")
                                                @RequestBody @Valid UsuarioDTO usuarioDTO) throws ResourceInvalidException {
        return new ResponseEntity<>(usuarios.guardarUsuario(usuarioDTO), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Modificar un usuario",notes = "Un usuario es quien accede al sistema. Puede tener rol de Gestor, vendedor.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = UsuarioDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@ApiParam(value = "Id del usuario a modificar",required = true,type = "int",example = "1")
                                                @PathVariable Long id,
                                            @ApiParam(value = "datos correspondientes a un usuario a crear",required = true,type = "UsuarioDTO.class")
                                            @RequestBody @Valid UsuarioDTO datos) throws ResourceInvalidException{
        //comprobar que el Id de la URL y del objeto usuario sean iguales
        if(datos.getId()!=id)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los id no coinciden");

        //return ResponseEntity.ok(usuarios.modificarUsuario(editar));
        return new ResponseEntity<>(usuarios.modificarUsuario(datos),HttpStatus.OK);
    }

    @ApiOperation(value = "Eliminar un usuario por id",notes = "Un usuario es quien accede al sistema. Puede tener rol de Gestor, vendedor.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@ApiParam(value = "Id del usuario a eliminar",required = true,type = "int",example = "1")
                                                @PathVariable Long id){
        usuarios.eliminar(id);
        return ResponseEntity.noContent().build();
    }



}

/*
@Operation(summary = "Get all foos")
    @ApiResponses(value = [
	ApiResponse(responseCode = "200", description = "Found Foos", content = [
            (Content(mediaType = "application/json", array = (
                ArraySchema(schema = Schema(implementation = Foo::class)))))]),
	ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
	ApiResponse(responseCode = "404", description = "Did not find any Foos", content = [Content()])]
* */


/* manejar errores de validacion manual usando BindingResult -> reemplazado por metodo en globalErrorHandler
@PostMapping("")
    //public ResponseEntity<?> guardarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO,BindingResult camposIncorrectos) throws ResourceInvalidException {
        List<String> errores= new ArrayList<>();

        if(camposIncorrectos.hasErrors()){
            for (var aux: camposIncorrectos.getFieldErrors()) {
                errores.add(aux.getField()+": " +aux.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
        }

        return new ResponseEntity<>(usuarios.guardarUsuario(usuarioDTO), HttpStatus.CREATED);
        }
* */