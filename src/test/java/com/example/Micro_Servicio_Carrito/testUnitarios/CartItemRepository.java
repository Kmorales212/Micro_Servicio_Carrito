package com.example.Micro_Servicio_Carrito.testUnitarios;

import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import com.example.Micro_Servicio_Carrito.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartItemRepositoryTest {

    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        cartItemRepository = mock(CartItemRepository.class);
    }

    @Test
    void testFindByUserId() {
        Long userId = 1L;
        List<CartItemModel> items = Arrays.asList(
            new CartItemModel(1L, userId, 10L, 1),
            new CartItemModel(2L, userId, 11L, 3)
        );
        
        when(cartItemRepository.findByUserId(userId)).thenReturn(items);

        List<List<CartItemModel>> result = Arrays.asList(cartItemRepository.findByUserId(userId));

        assertNotNull(result);
        assertEquals(2, items.size());
        verify(cartItemRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testFindByUserIdAndProductId() {
        Long userId = 1L;
        Long productId = 10L;
        CartItemModel item = new CartItemModel(1L, userId, productId, 2);
        
        when(cartItemRepository.findByUserIdAndProductId(userId, productId))
                .thenReturn(Optional.of(item));

        Optional<CartItemModel> found = cartItemRepository.findByUserIdAndProductId(userId, productId);

        assertTrue(found.isPresent());
        assertEquals(2, found.get().getQuantity());
        verify(cartItemRepository, times(1)).findByUserIdAndProductId(userId, productId);
    }

    @Test
    void testSave() {
        CartItemModel item = new CartItemModel(null, 1L, 10L, 5);
        when(cartItemRepository.save(any(CartItemModel.class))).thenReturn(new CartItemModel(1L, 1L, 10L, 5));

        CartItemModel saved = cartItemRepository.save(item);

        assertNotNull(saved.getId());
        assertEquals(5, saved.getQuantity());
    }

    @Test
    void testDeleteById() {
        doNothing().when(cartItemRepository).deleteById(1L);

        cartItemRepository.deleteById(1L);

        verify(cartItemRepository, times(1)).deleteById(1L);
    }
}