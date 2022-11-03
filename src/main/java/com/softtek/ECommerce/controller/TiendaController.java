package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.TiendaService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.Enums.MediosDePago;
import com.softtek.ECommerce.model.dto.MedioPagoCreateDTO;
import com.softtek.ECommerce.model.dto.PublicacionDTO;
import com.softtek.ECommerce.model.dto.TiendaCreateDTO;

import com.softtek.ECommerce.model.dto.TiendaDTO;
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
@Api(value = "Vendedores - Tienda", description = "Recurso accedido por vendedores para realizar operaciones sobre tiendas")
public class TiendaController {
    @Autowired
    private TiendaService tiendas;

    @ApiOperation(value = "Obtener las tiendas creadas",notes = "Una tienda se encarga de crear productos personalizados y publicarlos para vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TiendaDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/tiendas")
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
        return new ResponseEntity<>(tiendas.obtenerTodos(page), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener tienda por id",notes = "Una tienda se encarga de crear productos personalizados y publicarlos para vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TiendaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/tiendas/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id de la tienda a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(tiendas.buscarPorId(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Crear una tienda para un vendedor",notes = "Crea una tienda correspondiente a un vendedor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = TiendaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/vendedores/{id}/tiendas")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id del vendedor, dueño de la tienda",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a una tienda a crear",required = true,type = "TiendaCreateDTO.class")
                                        @RequestBody @Valid TiendaCreateDTO dto) throws ResourceInvalidException {
        return new ResponseEntity<>(tiendas.guardar(id,dto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Crear una tienda para un vendedor",notes = "Crea una tienda correspondiente a un vendedor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = TiendaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    //@PostMapping("/vendedores/{id}/tiendas/{tiendaId}/mediosPago")
    @PostMapping("/tiendas/{tiendaId}/mediosPago")
    public ResponseEntity<?> guardarMedioPago(//@PathVariable("id") Long id,
                                              @ApiParam(value = "Id de la tienda que agrega un medio de pago",required = true,type = "int",example = "1")
                                                @PathVariable("tiendaId") Long tiendaId,
                                              @ApiParam(value = "datos correspondientes a un medio de pago",required = true,type = "MedioPagoCreateDTO.class")
                                                @RequestBody @Valid MedioPagoCreateDTO datos) throws ResourceInvalidException {
        //return new ResponseEntity<>(tiendas.agregarMedioPago(id,datos), HttpStatus.CREATED);
        return new ResponseEntity<>(tiendas.agregarMedioPago(tiendaId,datos), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Eliminar medio de pago por id",notes = "Elimina un medio de pago correspondiente a una tienda")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    //@DeleteMapping("/vendedores/{id}/tiendas/{tiendaId}/mediosPago/{medioPagoId}")
    @DeleteMapping("/tiendas/{tiendaId}/mediosPago/{medioPagoId}")
    public ResponseEntity<?> eliminarMedioPago(//@PathVariable("id") Long id,
                                               @ApiParam(value = "Id de la tienda que elimina un medio de pago",required = true,type = "int",example = "1")
                                                    @PathVariable("tiendaId") Long tiendaId,
                                               @ApiParam(value = "Id del medio de pago a eliminar",required = true,type = "int",example = "1")
                                                    @PathVariable("medioPagoId") Long medioPagoId) throws ResourceInvalidException {
        //acomodar el eliminar medios de pago
        return new ResponseEntity<>(tiendas.eliminarMedioPago(tiendaId,medioPagoId), HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Editar tienda",notes = "Una tienda se encarga de crear productos personalizados y publicarlos para vender")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = TiendaDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    //@PutMapping("/vendedores/{id}/tiendas/{tiendaId}")
    @PutMapping("/tiendas/{tiendaId}")
    public ResponseEntity<?> editar(//@PathVariable("id") Long id,
                                    @ApiParam(value = "Id de la tienda a modificar",required = true,type = "int",example = "1")
                                        @PathVariable("tiendaId") Long tiendaId,
                                    @ApiParam(value = "datos correspondientes a una tienda",required = true,type = "TiendaCreateDTO.class")
                                        @RequestBody @Valid TiendaCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(tiendas.modificar(tiendaId,datos));
    }

    @ApiOperation(value = "Eliminar tienda por id",notes = "Una tienda se encarga de crear productos personalizados y publicarlos para vender")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/tiendas/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id de la tienda a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        tiendas.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
