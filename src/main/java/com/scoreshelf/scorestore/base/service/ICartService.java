package com.scoreshelf.scorestore.base.service;

import com.scoreshelf.scorestore.base.entity.Cart;

public interface ICartService {
    void addScoreToCart(String userId, String scoreId);
    Cart getCartByUserId(String userId);
    void removeScoreFromCart(String userId, String scoreId);
    void checkoutCart(String userId); 
    void saveCart(Cart cart);
}




