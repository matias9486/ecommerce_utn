package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.ProductoBaseService;
import com.softtek.ECommerce.Utils.Paginacion;
import com.softtek.ECommerce.model.dto.PosiblePersonalizacionDTO;
import com.softtek.ECommerce.model.dto.ProductoBaseCreateDTO;
import com.softtek.ECommerce.model.dto.ProductoBaseDTO;
import com.softtek.ECommerce.model.dto.ProductoBasePosiblePersonalizacionDTO;
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
@Api(value = "Gestores - Productos bases", description = "Recurso accedido por gestores para realizar operaciones sobre productos bases")
public class ProductoBaseController {
    @Autowired
    private ProductoBaseService productos;


    @ApiOperation(value = "Obtener los productos bases creados",notes = "Un producto base es un producto sin personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoBaseDTO[].class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/productosBase")
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

    @ApiOperation(value = "Obtener producto base por id",notes = "Un producto base es un producto sin personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok",response = ProductoBaseDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @GetMapping("/productosBase/{id}")
    public ResponseEntity<?> obtenerPorId(@ApiParam(value = "Id del producto base a buscar",required = true,type = "int",example = "1")
                                            @PathVariable("id") Long id){
        return  new ResponseEntity(productos.buscarPorId(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Crear un producto base",notes = "Un producto base es un producto sin personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = ProductoBaseDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/gestores/{id}/productosBase")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id del gestor que crea un producto base",required = true,type = "int",example = "1")
                                        @PathVariable("id") Long id,
                                     @ApiParam(value = "datos correspondientes a un producto base a crear",required = true,type = "ProductoBaseCreateDTO.class")
                                        @RequestBody @Valid ProductoBaseCreateDTO datos) throws ResourceInvalidException {
        return new ResponseEntity<>(productos.guardar(id,datos), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Modificar producto base por id",notes = "Un producto base es un producto sin personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK",response = ProductoBaseDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @PutMapping("/productosBase/{id}")
    public ResponseEntity<?> editar(@ApiParam(value = "Id del producto base a editar",required = true,type = "int",example = "1")
                                        @PathVariable Long id,
                                    @ApiParam(value = "datos correspondientes al producto base a editar",required = true,type = "ProductoBaseCreateDTO.class")
                                        @RequestBody @Valid ProductoBaseCreateDTO datos) throws ResourceInvalidException{
        return ResponseEntity.ok(productos.modificar(id,datos));
    }

    @ApiOperation(value = "Eliminar producto base por id",notes = "Un producto base es un producto sin personalizaciones aplicadas")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Sin Contenido"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No encontrado")
    })
    @DeleteMapping("/productosBase/{id}")
    public ResponseEntity<?> eliminar(@ApiParam(value = "Id del producto base a eliminar",required = true,type = "int",example = "1")
                                        @PathVariable Long id){
        productos.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //metodos para agregar posibles personalizaciones al producto base
    @ApiOperation(value = "Agregar una posible personalizacion a un producto base",notes = "Un gestor agegr una posible personaliazacion(area y tipo) a un producto base")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = ProductoBaseDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/gestores/{gestorId}/productosBase/{productoId}/posiblesPersonalizaciones")
    public ResponseEntity<?> guardar(@ApiParam(value = "Id del gestor que agregar la posible personalizacion",required = true,type = "int",example = "1")
                                        @PathVariable("gestorId") Long gestorId,
                                     @ApiParam(value = "Id de la posible personalizacion a agregar al producto base",required = true,type = "int",example = "1")
                                        @PathVariable("productoId") Long productoId,
                                     @ApiParam(value = "datos correspondientes al producto base y la posible personalizacion",required = true,type = "ProductoBasePosiblePersonalizacionDTO.class")
                                        @RequestBody @Valid ProductoBasePosiblePersonalizacionDTO dto) throws ResourceInvalidException {
        return new ResponseEntity<>(productos.agregarPosiblePersonalizacion(productoId,dto), HttpStatus.CREATED);
    }

}
