package com.example.Micro_Servicio_Carrito.testUnitarios;

import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import com.example.Micro_Servicio_Carrito.repository.CartItemRepository;
import com.example.Micro_Servicio_Carrito.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils; 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    private CartItemRepository cartItemRepository;
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        cartItemRepository = mock(CartItemRepository.class);
        
        cartItemService = new CartItemService();

        ReflectionTestUtils.setField(cartItemService, "cartItemRepository", cartItemRepository);
    }

    @Test
    void testGetCartByUserId() {
        Long userId = 1L;
        List<CartItemModel> items = Arrays.asList(
            new CartItemModel(1L, userId, 10L, 2),
            new CartItemModel(2L, userId, 11L, 1)
        );
        when(cartItemRepository.findByUserId(userId)).thenReturn(items);

        List<CartItemModel> result = cartItemService.getCartByUserId(userId);

        assertEquals(2, result.size());
        verify(cartItemRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testAddOrUpdateItem_UpdateExisting() {
        Long userId = 1L;
        Long productId = 10L;
        int initialQuantity = 2;
        int addedQuantity = 3;

        CartItemModel existingItem = new CartItemModel(1L, userId, productId, initialQuantity);
        
        when(cartItemRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.of(existingItem));
        when(cartItemRepository.save(any(CartItemModel.class))).thenAnswer(i -> i.getArgument(0));

        CartItemModel result = cartItemService.addOrUpdateItem(userId, productId, addedQuantity);

        assertNotNull(result);
        assertEquals(5, result.getQuantity()); // 2 + 3
        verify(cartItemRepository, times(1)).save(existingItem);
    }

    @Test
    void testAddOrUpdateItem_AddNew() {
        Long userId = 1L;
        Long productId = 20L;
        int quantity = 5;

        when(cartItemRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.empty());
        when(cartItemRepository.save(any(CartItemModel.class))).thenAnswer(i -> i.getArgument(0));

        CartItemModel result = cartItemService.addOrUpdateItem(userId, productId, quantity);

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());
        assertNull(result.getId()); // Es nuevo
        verify(cartItemRepository, times(1)).save(any(CartItemModel.class));
    }

    @Test
    void testDeleteItem() {
        cartItemService.deleteItem(1L);

        verify(cartItemRepository, times(1)).deleteById(1L);
    }
}