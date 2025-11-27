package com.example.Micro_Servicio_Carrito.service;

import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import com.example.Micro_Servicio_Carrito.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItemModel> getCartByUserId(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
    
    public CartItemModel addOrUpdateItem(Long userId, Long productId, int quantity) {
        Optional<CartItemModel> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItemModel item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        } else {
            CartItemModel newItem = new CartItemModel(null, userId, productId, quantity);
            return cartItemRepository.save(newItem);
        }
    }
    
    public void deleteItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}