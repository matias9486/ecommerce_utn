package com.softtek.ECommerce.controller;

import com.softtek.ECommerce.Exceptions.ResourceInvalidException;
import com.softtek.ECommerce.Service.CarroComprasService;
import com.softtek.ECommerce.Service.ItemsService;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.Item;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="")
@Api(value = "Compradores y Vendedores- Compras", description = "Recurso accedido por compradores para realizar compras y por vendedores para confirmar pagos recibidos")
public class CarroComprasController {
    @Autowired
    private CarroComprasService carros;
    @Autowired
    private ItemsService items;

    @ApiOperation(value = "Agregar primer item(producto) a carrito",notes = "Se agrega un producto a un carrito vacio")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = CarroCompraDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("compras/publicaciones/{id}")
    public ResponseEntity<?> crearCarro(@ApiParam(value = "Id perteneciente a la publicacion de un producto a agregar al carrito",required = true,type = "Long",example = "1")
                                            @PathVariable("id") Long publicacionId,
                                        @ApiParam(value = "Cantidad de productos/publicaciones a agregar al carrito",required = true,type = "ItemCreateDTO.class")
                                            @RequestBody @Valid ItemCreateDTO cantidad) throws ResourceInvalidException {
        Item item=items.guardar(publicacionId,cantidad);
        return new ResponseEntity<>(carros.crearCarroConItem(item), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Agregar item(producto) a carrito existente",notes = "Se agrega un producto a un carrito con elementos")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = CarroCompraDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/compras/{id}/items")
    public ResponseEntity<?> agregarItemACarro(@ApiParam(value = "Id perteneciente al carrito de compras al cual se agregan items",required = true,type = "Long",example = "1")
                                                    @PathVariable("id") Long carroId,
                                               @ApiParam(value = "Representa el producto/publicacion y la cantidad a agregar al carrito",required = true,type = "ItemPublicacionDTO.class")
                                                    @RequestBody @Valid ItemPublicacionDTO item) throws ResourceInvalidException {
        return new ResponseEntity<>(carros.agregarItemACarro(carroId,item), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Comprador confirma compra",notes = "Comprador confirma compra agregando datos personales y medio de pago")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = CompraDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/compras/{id}/confirmar_compra")
    public ResponseEntity<?> confirmarCompra(@ApiParam(value = "Id perteneciente al carrito que confirma compra",required = true,type = "Long",example = "1")
                                                @PathVariable("id") Long carroId,
                                             @ApiParam(value = "Representa el producto/publicacion y la cantidad a agregar al carrito",required = true,type = "CompraCreateDTO.class")
                                                @RequestBody @Valid CompraCreateDTO datosCompra) throws ResourceInvalidException {
        return new ResponseEntity<>(carros.confirmarCompra(carroId,datosCompra), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Vendedor confirma pago",notes = "Vendedor confirma pago recibido por una compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Creado",response = CompraDTO.class),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @PostMapping("/compras/{id}/confirmar_pago")
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<?> confirmarPago(@ApiParam(value = "Id perteneciente a una compra de la cual se confirmara el pago recibido",required = true,type = "Long",example = "1")
                                                @PathVariable("id") Long compraId) throws ResourceInvalidException {
        return new ResponseEntity<>(carros.confirmarPago(compraId), HttpStatus.OK);
    }

    @ApiOperation(value = "Vendedor genera factura",notes = "Vendedor genera factura de una compra")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 400, message = "Peticion incorrecta"),
            @ApiResponse(code = 401, message = "No Autorizado"),
            @ApiResponse(code = 403, message = "Acceso denegado"),
            @ApiResponse(code = 404, message = "No se encontro Gestor")
    })
    @GetMapping("/compras/{id}/")
    @PreAuthorize("hasRole('VENDEDOR')")
    public ResponseEntity<?> generarFactura(@ApiParam(value = "Id perteneciente a una compra de la cual se generara factura",required = true,type = "Long",example = "1")
                                           @PathVariable("id") Long compraId) throws ResourceInvalidException {
        return new ResponseEntity<>(carros.generarFactura(compraId), HttpStatus.OK);
    }

}
