package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.ProductoBaseService;
import com.softtek.ECommerce.Service.PublicacionesService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.Enums.EstadoPublicacion;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.Publicacion;

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
@Api(value = "Vendedores - Publicaciones", description = "Recurso accedido por vendedores para realizar operaciones sobre publicaciones")
public class PublicacionesController {

    @Autowired
    private PublicacionesService publicaciones;

    @ApiOperation(value = "Obtener las publicaciones creadas",notes = "Una publicacion es una publicacion de productos a vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PublicacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/publicaciones")
    ResponseEntity<?> obtener(@ApiParam(value = "Nro de Pagina",required = false,type = "int",example = "0")
                              @RequestParam(value = "pageNro", defaultValue = Paginacion.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                              @ApiParam(value = "Tamaño de pagina",required = false,type = "int",example = "10")
                              @RequestParam(value = "pageSize", defaultValue = Paginacion.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                              @ApiParam(value = "Ordenar por",required = false,type = "String",example = "id")
                              @RequestParam(value = "sortBy", defaultValue = Paginacion.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
                              @ApiParam(value = "Tipo Ordenamiento",required = false,type = "String",example = "ASC")
                              @RequestParam(value = "sortDir", defaultValue = Paginacion.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable page = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        return new ResponseEntity<>(publicaciones.obtenerTodos(page), HttpStatus.OK);
    }


    @ApiOperation(value = "Obtener publicacion por id",notes = "Una publicacion es una publicacion de productos a vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PublicacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/publicaciones/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id de la publicacion a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(publicaciones.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear una publicacion",notes = "Crea una publicacion correspondiente a una tienda")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = PublicacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/tiendas/{id}/publicaciones")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id de la tienda que crea una publicacion",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a una publicacion a crear",required = true,type = "PublicacionCreateDTO.class")
                                        @RequestBody @Valid PublicacionCreateDTO dto) throws ResourceInvalidException {
        return new ResponseEntity<>(publicaciones.guardar(id,dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Editar una publicacion",notes = "Una publicacion es una publicacion de productos a vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PublicacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PutMapping("/publicaciones/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id de la publicacion a modificar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a una publicacion a modificar",required = true,type = "PublicacionCreateDTO.class")
                                        @RequestBody @Valid PublicacionCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(publicaciones.modificar(id,datos));
    }

    @ApiOperation(value = "Eliminar publicacion por id",notes = "Una publicacion es una publicacion de productos a vender")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/publicaciones/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id de la publicacion a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        publicaciones.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //metodos para cambiar estado
    @ApiOperation(value = "Editar el estado de una publicacion",notes = "Una publicacion es una publicacion de productos a vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PublicacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PutMapping("/publicaciones/{id}/estado")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id de la publicacion a modificar estado",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long publicacionId,
                                     @ApiParam(value = "datos correspondientes al estado de una publicacion",required = true,type = "EstadoPublicacionDTO.class")
                                        @RequestBody @Valid EstadoPublicacionDTO estado) throws ResourceInvalidException {
        return new ResponseEntity<>(publicaciones.cambiarEstado(publicacionId,estado), HttpStatus.CREATED);
    }
}
