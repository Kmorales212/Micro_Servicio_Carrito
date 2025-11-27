package com.example.Micro_Servicio_Carrito.repository;

import com.example.Micro_Servicio_Carrito.model.CartItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemModel, Long> {
    List<CartItemModel> findByUserId(Long userId);
    
    Optional<CartItemModel> findByUserIdAndProductId(Long userId, Long productId);
}