package com.example.Micro_Servicio_Carrito.testUnitarios;

import com.example.Micro_Servicio_Carrito.controller.CartItemController;
import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import com.example.Micro_Servicio_Carrito.service.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CartItemControllerTest {

    private MockMvc mockMvc;
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        cartItemService = mock(CartItemService.class);

        CartItemController cartItemController = new CartItemController();

        ReflectionTestUtils.setField(cartItemController, "cartItemService", cartItemService);

        this.mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

    @Test
    void testGetCart() throws Exception {
        Long userId = 1L;
        CartItemModel item1 = new CartItemModel(1L, userId, 2L, 2);
        CartItemModel item2 = new CartItemModel(2L, userId, 3L, 1);

        when(cartItemService.getCartByUserId(userId)).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/cart/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].productId").value(2))
                .andExpect(jsonPath("$[1].productId").value(3));

        verify(cartItemService, times(1)).getCartByUserId(userId);
    }

    @Test
    void testAddItemToCart() throws Exception {
        Long userId = 1L;
        Long productId = 1L;
        int quantity = 2;
        CartItemModel savedItem = new CartItemModel(10L, userId, productId, quantity);

        when(cartItemService.addOrUpdateItem(userId, productId, quantity)).thenReturn(savedItem);

        mockMvc.perform(post("/api/cart/{userId}/add", userId)
                .param("productId", productId.toString())
                .param("quantity", String.valueOf(quantity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.quantity").value(2));

        verify(cartItemService, times(1)).addOrUpdateItem(userId, productId, quantity);
    }

    @Test
    void testDeleteItem() throws Exception {
        Long itemId = 10L;
        doNothing().when(cartItemService).deleteItem(itemId);

        mockMvc.perform(delete("/api/cart/{itemId}", itemId))
                .andExpect(status().isNoContent());

        verify(cartItemService, times(1)).deleteItem(itemId);
    }
}