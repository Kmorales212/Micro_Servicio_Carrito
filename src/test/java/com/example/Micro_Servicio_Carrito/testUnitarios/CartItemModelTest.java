package com.example.Micro_Servicio_Carrito.testUnitarios;

import com.example.Micro_Servicio_Carrito.model.CartItemModel; 
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartItemModelTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        Long id = 1L;
        Long userId = 100L;
        Long productId = 10L;
        int quantity = 2;

        CartItemModel cartItem = new CartItemModel(id, userId, productId, quantity);

        assertEquals(id, cartItem.getId());
        assertEquals(userId, cartItem.getUserId());
        assertEquals(productId, cartItem.getProductId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        CartItemModel cartItem = new CartItemModel();
        
        cartItem.setId(5L);
        cartItem.setUserId(200L);
        cartItem.setProductId(20L);
        cartItem.setQuantity(12);

        assertEquals(5L, cartItem.getId());
        assertEquals(200L, cartItem.getUserId());
        assertEquals(20L, cartItem.getProductId());
        assertEquals(12, cartItem.getQuantity());
    }

    @Test
    void testEqualsAndHashCode() {
        CartItemModel item1 = new CartItemModel(1L, 100L, 10L, 2);
        CartItemModel item2 = new CartItemModel(1L, 100L, 10L, 2);
        
        CartItemModel item3 = new CartItemModel(2L, 100L, 11L, 1);

        assertEquals(item1, item2);        
        assertNotEquals(item1, item3);     
        assertNotEquals(item1, null);   
        
        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testToString() {
        CartItemModel cartItem = new CartItemModel(1L, 100L, 10L, 5);
        String toString = cartItem.toString();

        assertTrue(toString.contains("userId=100"));
        assertTrue(toString.contains("productId=10"));
        assertTrue(toString.contains("quantity=5"));
        assertTrue(toString.contains("id=1"));
    }
}