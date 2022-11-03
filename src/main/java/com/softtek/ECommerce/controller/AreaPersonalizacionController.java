package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.AreaPersonalizacionService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.AreaPersonalizacionCreateDTO;
import com.softtek.ECommerce.model.dto.AreaPersonalizacionDTO;
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
@Api(value = "Gestores - Area Personalizaciones", description = "Recurso accedido por gestores para realizar operaciones sobre Areas de personalizacion")
public class AreaPersonalizacionController {
    @Autowired
    private AreaPersonalizacionService areas;

    @ApiOperation(value = "Obtener las areas creadas filtrada por nombre",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = AreaPersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/areasPersonalizacion/ConCriteria")
    ResponseEntity<?> obtenerFiltradosConCriteria(
            @ApiParam(value = "Nombre por el cual se filtrara",required = false,type = "String",example = "Frente")
            @RequestParam(value = "nombre", required = false) String nombre) {
        return new ResponseEntity<>(areas.obtenerTodosFiltradosConCriteria(nombre),HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener las areas creadas",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = AreaPersonalizacionDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/areasPersonalizacion")
    ResponseEntity<?> obtenerFiltrados(
                @ApiParam(value = "Nro de Pagina",required = false,type = "int",example = "0")
                    @RequestParam(value = "pageNro", defaultValue = Paginacion.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
                @ApiParam(value = "Tamaño de pagina",required = false,type = "int",example = "10")
                    @RequestParam(value = "pageSize", defaultValue = Paginacion.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                @ApiParam(value = "Ordenar por",required = false,type = "String",example = "id")
                    @RequestParam(value = "sortBy", defaultValue = Paginacion.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
                @ApiParam(value = "Tipo Ordenamiento",required = false,type = "String",example = "ASC")
                    @RequestParam(value = "sortDir", defaultValue = Paginacion.ORDENAR_DIRECCION_POR_DEFECTO, required = false) String sortDir,
                @ApiParam(value = "campo por el cual se filtrara",required = false,type = "String",example = "Frente")
                    @RequestParam(value = "nombre", required = false) String nombre) {
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
                    : Sort.by(ordenarPor).descending();
            Pageable page = PageRequest.of(numeroDePagina, medidaDePagina, sort);
        return new ResponseEntity<>(areas.obtenerTodosFiltrado(nombre, page),HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener un area en base a su id",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = AreaPersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/areasPersonalizacion/{id}")
    public ResponseEntity<?> obtenerAreaPorId(@ApiParam(value = "Id del area a buscar",required = true,type = "int",example = "1")
                                                  @PathVariable("id") Long id){
        return  new ResponseEntity(areas.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear un area nueva",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = AreaPersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/gestores/{id}/areasPersonalizacion")
    public ResponseEntity<?> guardarArea(@ApiParam(value = "Id perteneciente al gestor que crea el area",required = true,type = "Long",example = "1")
                                             @PathVariable("id") Long id,
                                         @ApiParam(value = "datos correspondientes al area a crear",required = true,type = "AreaPersonalizacionCreateDTO.class")
                                             @RequestBody @Valid AreaPersonalizacionCreateDTO area) throws ResourceInvalidException {
        return new ResponseEntity<>(areas.guardarArea(id,area), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Editar un area por id",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = AreaPersonalizacionDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @PutMapping("/areasPersonalizacion/{id}")
    public ResponseEntity<?> editarArea(@ApiParam(value = "Id del area a editar",required = true,type = "int",example = "1")
                                            @PathVariable Long id,
                                        @ApiParam(value = "datos correspondientes al area a editar",required = true,type = "AreaPersonalizacionCreateDTO.class")
                                            @RequestBody @Valid AreaPersonalizacionCreateDTO area) throws ResourceInvalidException{
        return ResponseEntity.ok(areas.modificarArea(id,area));
    }


    @ApiOperation(value = "Eliminar un area por id",notes = "Una area es el sector en donde se realizara una personalizacion")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/areasPersonalizacion/{id}")
    public ResponseEntity<?> eliminarArea(@ApiParam(value = "Id del area a eliminar",required = true,type = "int",example = "1")
                                              @PathVariable Long id){
        areas.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
