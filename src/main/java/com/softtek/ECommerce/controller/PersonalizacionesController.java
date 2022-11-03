package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.PersonalizacionService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.AreaPersonalizacionDTO;
import com.softtek.ECommerce.model.dto.PersonalizacionCreateDTO;

import com.softtek.ECommerce.model.dto.PersonalizacionDTO;
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
@Api(value = "Vendedores - Personalizaciones", description = "Recurso accedido por vendedores para realizar operaciones sobre personalizaciones")
public class PersonalizacionesController {
    @Autowired
    private PersonalizacionService personalizaciones;

    @ApiOperation(value = "Obtener las personalizaciones creadas",notes = "Una personalizacion corresponde a que contenido se aplicara a un producto personalizado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/personalizaciones")
    ResponseEntity<?> obtener(@ApiParam(value = "Nro de Pagina",required = false,type = "int",example = "0")
                              @RequestParam(value = "pageNro", defaultValue = Paginacion.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                              @ApiParam(value = "Tamaño de pagina",required = false,type = "int",example = "10")
                              @RequestParam(value = "pageSize", defaultValue = Paginacion.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                              @ApiParam(value = "Ordenar por",required = false,type = "String",example = "id")
                              @RequestParam(value = "sortBy", defaultValue = Paginacion.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
                              @ApiParam(value = "Tipo Ordenamiento",required = false,type = "String",example = "ASC")
                              @RequestParam(value = "sortDir", defaultValue = Paginacion.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir,
                              @ApiParam(value = "campo por el cual se filtrara",required = false,type = "String",example = "Logo")
                              @RequestParam(value = "nombre", required = false) String nombre) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                : Sort.by(ordenarPor).descending();
        Pageable page = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        return new ResponseEntity<>(personalizaciones.obtenerTodos(nombre,page), HttpStatus.OK);
    }


    @ApiOperation(value = "Obtener una personalizacion en base a su id",notes = "Una personalizacion corresponde a que contenido se aplicara a un producto personalizado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = PersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/personalizaciones/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id de la personalizacion a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(personalizaciones.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear una personalizacion nueva",notes = "Una personalizacion corresponde a que contenido se aplicara a un producto personalizado.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = PersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/tiendas/{id}/personalizaciones")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id perteneciente a la tienda que crea la personalizacion",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a la personalizacion a crear",required = true,type = "PersonalizacionCreateDTO.class")
                                        @RequestBody @Valid PersonalizacionCreateDTO datosPersonalizacion) throws ResourceInvalidException {
        return new ResponseEntity<>(personalizaciones.guardar(id,datosPersonalizacion), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Editar una personalizacion por id",notes = "Una personalizacion corresponde a que contenido se aplicara a un producto personalizado.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = PersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @PutMapping("personalizaciones/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id perteneciente a la personalizacion a modificar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a la personalizacion a editar",required = true,type = "PersonalizacionCreateDTO.class")
                                        @RequestBody @Valid PersonalizacionCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(personalizaciones.modificar(id,datos));
    }

    @ApiOperation(value = "Eliminar una personalizacion por id",notes = "Una personalizacion corresponde a que contenido se aplicara a un producto personalizado.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/personalizaciones/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id perteneciente a la personalizacion a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        personalizaciones.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
