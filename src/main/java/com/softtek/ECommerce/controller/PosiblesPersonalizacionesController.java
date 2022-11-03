package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.PosiblePersonalizacionService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.PersonalizacionDTO;
import com.softtek.ECommerce.model.dto.PersonalizacionEspecificaDTO;
import com.softtek.ECommerce.model.dto.PosiblePersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.PosiblePersonalizacionDTO;
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
@Api(value = "Gestores - Posibles Personalizaciones", description = "Recurso accedido por gestores para realizar operaciones sobre posibles personalizaciones")
public class PosiblesPersonalizacionesController {
    @Autowired
    private PosiblePersonalizacionService personalizaciones;


    @ApiOperation(value = "Obtener las posibles personalizaciones creadas",notes = "Una posible personalizacion es la combinacion entre area y tipo de personalizacion que se podra aplicar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PosiblePersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/posiblesPersonalizacion")
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
        return new ResponseEntity<>(personalizaciones.obtenerTodos(page), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener posible personalizacion por id",notes = "Una posible personalizacion es la combinacion entre area y tipo de personalizacion que se podra aplicar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PosiblePersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/posiblesPersonalizacion/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id de la posible personalizacion a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(personalizaciones.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear una posible personalizacion",notes = "Una posible personalizacion es la combinacion entre area y tipo de personalizacion que se podra aplicar a un producto")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = PosiblePersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/gestores/{id}/posiblesPersonalizacion")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id del gestor que crea una posible personalizacion",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a la posible personalizacion a crear",required = true,type = "PosiblePersonalizacionCreateDTO.class")
                                        @RequestBody @Valid PosiblePersonalizacionCreateDTO datos) throws ResourceInvalidException {
        return new ResponseEntity<>(personalizaciones.guardar(id,datos), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Modificar una posible personalizacion por id",notes = "Una posible personalizacion es la combinacion entre area y tipo de personalizacion que se podra aplicar a un producto")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = PosiblePersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @PutMapping("/posiblesPersonalizacion/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id de la posible personalizacion a editar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a la posible personalizacion a editar",required = true,type = "PosiblePersonalizacionCreateDTO.class")
                                        @RequestBody @Valid PosiblePersonalizacionCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(personalizaciones.modificar(id,datos));
    }

    @ApiOperation(value = "Eliminar posible personalizacion por id",notes = "Una posible personalizacion es la combinacion entre area y tipo de personalizacion que se podra aplicar a un producto")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/posiblesPersonalizacion/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id de la posible personalizacion a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        personalizaciones.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
