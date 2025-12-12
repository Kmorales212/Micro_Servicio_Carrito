package com.example.Micro_Servicio_Carrito.controller;

import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import com.example.Micro_Servicio_Carrito.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
// 1. Título General para el Controlador
@Tag(name = "Carrito de Compras", description = "Operaciones para agregar, listar y eliminar productos del carrito de usuario")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    // --- OBTENER CARRITO ---
    @Operation(summary = "Obtener carrito por Usuario", description = "Devuelve la lista de todos los productos que un usuario tiene en su carrito")
    @ApiResponse(responseCode = "200", description = "Carrito recuperado exitosamente")
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemModel>> getCart(
            @Parameter(description = "ID del usuario dueño del carrito") @PathVariable Long userId) {
        return ResponseEntity.ok(cartItemService.getCartByUserId(userId));
    }
    
    // --- AGREGAR ÍTEM ---
    @Operation(summary = "Agregar producto al carrito", description = "Añade un producto nuevo o actualiza la cantidad si ya existe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto agregado o actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos de entrada")
    })
    @PostMapping("/{userId}/add")
    public ResponseEntity<CartItemModel> addItemToCart(
            @Parameter(description = "ID del usuario") @PathVariable Long userId, 
            @Parameter(description = "ID del producto a agregar") @RequestParam Long productId, 
            @Parameter(description = "Cantidad a agregar (por defecto 1)") @RequestParam(defaultValue = "1") int quantity) {
        
        CartItemModel savedItem = cartItemService.addOrUpdateItem(userId, productId, quantity);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }
    
    // --- ELIMINAR ÍTEM ---
    @Operation(summary = "Eliminar un ítem del carrito", description = "Borra un registro específico del carrito usando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Ítem eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "El ítem no existe")
    })
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "ID único del ítem en el carrito") @PathVariable Long itemId) {
        cartItemService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}