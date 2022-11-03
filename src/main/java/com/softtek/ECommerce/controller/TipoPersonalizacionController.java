package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.TipoPersonalizacionService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.TiendaDTO;
import com.softtek.ECommerce.model.dto.TipoPersonalizacionCreateDTO;

import com.softtek.ECommerce.model.dto.TipoPersonalizacionDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="")
@Api(value = "Gestores - Tipo de personalizacion", description = "Recurso accedido por gestores para realizar operaciones sobre tipos de personalizacion")
public class TipoPersonalizacionController {
    @Autowired
    private TipoPersonalizacionService tipos;

    @ApiOperation(value = "Obtener los tipos de personalizacion creados",notes = "Un tipo de personalizacion indica el tipo de personalizacion(Imagen,Texto por ejemplo) a agregar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TipoPersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/tiposPersonalizacion")
    ResponseEntity<?> obtenerFiltrados(@ApiParam(value = "Nro de Pagina",required = false,type = "int",example = "0")
                                       @RequestParam(value = "pageNro", defaultValue = Paginacion.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                                       @ApiParam(value = "Tamaño de pagina",required = false,type = "int",example = "10")
                                       @RequestParam(value = "pageSize", defaultValue = Paginacion.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                                       @ApiParam(value = "Ordenar por",required = false,type = "String",example = "id")
                                       @RequestParam(value = "sortBy", defaultValue = Paginacion.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
                                       @ApiParam(value = "Tipo Ordenamiento",required = false,type = "String",example = "ASC")
                                       @RequestParam(value = "sortDir", defaultValue = Paginacion.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir,
                                       @ApiParam(value = "campo por el cual se filtrara",required = false,type = "String",example = "Imagen")
                                       @RequestParam(value = "nombre", required = false) String nombre) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable page = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        return new ResponseEntity<>(tipos.obtenerTodosFiltrado(nombre, page), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener tipo de personalizacion por id",notes = "Un tipo de personalizacion indica el tipo de personalizacion(Imagen,Texto por ejemplo) a agregar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TipoPersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/tiposPersonalizacion/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id del tipo de personalizacion a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(tipos.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear un tipo de personalizacion",notes = "Un gestor crea un tipo de personalizacion. Un tipo de personalizacion indica el tipo de personalizacion(Imagen,Texto por ejemplo) a agregar a un producto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = TiendaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/gestores/{id}/tiposPersonalizacion")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id del gestor que crea el tipo de personalizacion",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a un tipo de personalizacion a crear",required = true,type = "TipoPersonalizacionCreateDTO.class")
                                        @RequestBody @Valid TipoPersonalizacionCreateDTO datos) throws ResourceInvalidException {
        return new ResponseEntity<>(tipos.guardarTipo(id,datos), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Modificar un tipo de personalizacion",notes = "Un tipo de personalizacion indica el tipo de personalizacion(Imagen,Texto por ejemplo) a agregar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TipoPersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PutMapping("/tiposPersonalizacion/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id del tipo de personalizacion a modificar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a un tipo de personalizacion a modificar",required = true,type = "TipoPersonalizacionCreateDTO.class")
                                        @RequestBody @Valid TipoPersonalizacionCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(tipos.modificarTipo(id,datos));
    }

    @ApiOperation(value = "Eliminar un tipo de personalizacion por id",notes = "Un tipo de personalizacion indica el tipo de personalizacion(Imagen,Texto por ejemplo) a agregar a un producto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/tiposPersonalizacion/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id del tipo de personalizacion a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        tipos.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
