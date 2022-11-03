package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.PersonalizacionesEspecificasService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.PersonalizacionDTO;
import com.softtek.ECommerce.model.dto.PersonalizacionEspecificaCreateDTO;
import com.softtek.ECommerce.model.dto.PersonalizacionEspecificaDTO;
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
@Api(value = "Vendedores - Personalizaciones Especificas", description = "Recurso accedido por vendedores para realizar operaciones sobre personalizaciones especificas")
public class PersonalizacionesEspecificasController {
    @Autowired
    private PersonalizacionesEspecificasService personalizaciones;

    @ApiOperation(value = "Obtener las personalizaciones especificas creadas",notes = "Una personalizacion especifica es la combinacion entre personalizacion(imagen,frase,etc) y una posible personalizacion(area y tipo) que se podra aplicar a un producto personalizado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/personalizacionesEspecificas")
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


    @ApiOperation(value = "Obtener una personalizacion especifica por id",notes = "Una personalizacion especifica es la combinacion entre personalizacion(imagen,frase,etc) y una posible personalizacion(area y tipo) que se podra aplicar a un producto personalizado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PersonalizacionEspecificaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/personalizacionesEspecificas/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id de la personalizacion especifica a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(personalizaciones.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear una personalizacion especifica",notes = "Una personalizacion especifica es la combinacion entre personalizacion(imagen,frase,etc) y una posible personalizacion(area y tipo) que se podra aplicar a un producto personalizado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = PersonalizacionEspecificaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/tiendas/{id}/personalizacionesEspecificas")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id de la tienda que crea una personalizacion especifica",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a la personalizacion especifica a crear",required = true,type = "PersonalizacionEspecificaCreateDTO.class")
                                        @RequestBody @Valid PersonalizacionEspecificaCreateDTO datos) throws ResourceInvalidException {
        return new ResponseEntity<>(personalizaciones.guardar(id,datos), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Editar una personalizacion especifica por id",notes = "Una personalizacion especifica es la combinacion entre personalizacion(imagen,frase,etc) y una posible personalizacion(area y tipo) que se podra aplicar a un producto personalizado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = PersonalizacionEspecificaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @PutMapping("/personalizacionesEspecificas/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id perteneciente a la personalizacion especifica a modificar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a la personalizacion especifica a editar",required = true,type = "PersonalizacionEspecificaCreateDTO.class")
                                        @RequestBody @Valid PersonalizacionEspecificaCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(personalizaciones.modificar(id,datos));
    }

    @ApiOperation(value = "Eliminar una personalizacion especifica por id",notes = "Una personalizacion especifica es la combinacion entre personalizacion(imagen,frase,etc) y una posible personalizacion(area y tipo) que se podra aplicar a un producto personalizado")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/personalizacionesEspecificas/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id perteneciente a la personalizacion especifica a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        personalizaciones.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
