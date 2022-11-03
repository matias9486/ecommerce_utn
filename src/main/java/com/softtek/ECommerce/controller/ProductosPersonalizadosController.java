package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.PersonalizacionesEspecificasService;
import com.softtek.ECommerce.Service.ProductosPersonalizadosService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.ProductoPersonalizado;

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
@Api(value = "Vendedores - Productos personalizados", description = "Recurso accedido por vendedores para realizar operaciones sobre productos personalizados")
public class ProductosPersonalizadosController {
    @Autowired
    private ProductosPersonalizadosService productos;

    @ApiOperation(value = "Obtener los productos personalizados creados",notes = "Un producto personalizado es un producto con personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoPersonalizadoDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/productosPersonalizados")
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
        return new ResponseEntity<>(productos.obtenerTodos(page), HttpStatus.OK);
    }

    @ApiOperation(value = "Obtener producto personalizado por id",notes = "Un producto personalizado es un producto con personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoPersonalizadoDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/productosPersonalizados/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id del producto personalizado a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(productos.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear un producto personalizado",notes = "Un producto personalizado es un producto con personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = ProductoPersonalizadoDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/tiendas/{id}/productoPersonalizados")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id de la tienda que crea un producto personalizado",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a un producto personalizado a crear",required = true,type = "ProductoPersonalizadoCreateDTO.class")
                                        @RequestBody @Valid ProductoPersonalizadoCreateDTO datos) throws ResourceInvalidException {
        return new ResponseEntity<>(productos.guardar(id,datos), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Agregar una personalizacion especifica a un producto personalizado",notes = "Una personalizacion especifica es la combinacion de una personalizacion(imagen/frase y contenido) y una posible personalizacion(area y tipo de personalizacion)")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = ProductoPersonalizadoDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/productosPersonalizados/{id}/personalizacionEspecifica")
    public ResponseEntity<?> agregarPersonalizacionEspecifica(@ApiParam(value = "Id del producto personalizado al cual se le agrega una personalizacion especifica",required = true,type = "int",example = "1")
                                                                @PathVariable Long id,
                                                              @ApiParam(value = "datos correspondientes a una personalizacion especifica",required = true,type = "ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO.class")
                                                                @RequestBody @Valid ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(productos.agregarPersonalizacionEspecifica(id,datos));
    }

    @ApiOperation(value = "Eliminar una personalizacion especifica de un producto personalizado",notes = "Una personalizacion especifica es la combinacion de una personalizacion(imagen/frase y contenido) y una posible personalizacion(area y tipo de personalizacion)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoPersonalizadoDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @DeleteMapping("/productosPersonalizados/{productoId}/personalizacionEspecifica/{personalizacionId}")
    public ResponseEntity<?> eliminarPersonalizacionEspecifica(@ApiParam(value = "Id del producto personalizado al cual se le eliminara una personalizacion especifica",required = true,type = "int",example = "1")
                                                                    @PathVariable Long productoId,
                                                               @ApiParam(value = "Id de la personalizacion especifica a eliminar del producto",required = true,type = "int",example = "1")
                                                                @PathVariable Long personalizacionId) throws ResourceInvalidException{
        return ResponseEntity.ok(productos.eliminarPersonalizacionEspecifica(productoId,personalizacionId));
    }

    @ApiOperation(value = "Editar un producto personalizado",notes = "Un producto personalizado es un producto con personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoPersonalizadoDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PutMapping("/productosPersonalizados/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id del producto personalizado a modificar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes a un producto personalizado",required = true,type = "ProductoPersonalizadoCreateDTO.class")
                                    @RequestBody @Valid ProductoPersonalizadoCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(productos.modificar(id,datos));
    }


    @ApiOperation(value = "Eliminar producto personalizado por id",notes = "Un producto personalizado es un producto con personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/productosPersonalizados/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id del producto personalizado a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        productos.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
