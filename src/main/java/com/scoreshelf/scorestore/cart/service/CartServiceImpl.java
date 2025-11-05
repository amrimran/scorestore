package com.scoreshelf.scorestore.cart.service;

import com.scoreshelf.scorestore.auth.service.AuthServiceImpl;
import com.scoreshelf.scorestore.base.entity.Cart;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.repository.CartRepository;
import com.scoreshelf.scorestore.base.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthServiceImpl authService;

    @Override
    public void addScoreToCart(String userId, String scoreId) {
        if (userId == null || scoreId == null) {
            throw new IllegalArgumentException("User ID and Score ID must not be null");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {

            cart = new Cart();
            cart.setCartId(generateUniqueCartId());
            cart.setUserId(userId);
            cart.setScoreIds(scoreId);
        } else {

            String scoreIds = cart.getScoreIds();
            if (scoreIds == null || scoreIds.isEmpty()) {
                cart.setScoreIds(scoreId);
            } else {
                List<String> scoreIdList = Arrays.asList(scoreIds.split(","));
                if (!scoreIdList.contains(scoreId)) {
                    cart.setScoreIds(scoreIds + "," + scoreId);
                }
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void removeScoreFromCart(String userId, String scoreId) {
        if (userId == null || scoreId == null) {
            throw new IllegalArgumentException("User ID and Score ID must not be null");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            String scoreIds = cart.getScoreIds();
            if (scoreIds != null && !scoreIds.isEmpty()) {

                List<String> scoreIdList = Arrays.asList(scoreIds.split(","));
                scoreIdList = scoreIdList.stream()
                        .filter(id -> !id.equals(scoreId))
                        .collect(Collectors.toList());

                if (scoreIdList.isEmpty()) {

                    cartRepository.delete(cart);
                } else {

                    cart.setScoreIds(String.join(",", scoreIdList));
                    cartRepository.save(cart);
                }
            }
        }
    }

    @Override
    public void checkoutCart(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null && cart.getScoreIds() != null && !cart.getScoreIds().isEmpty()) {

            List<String> scoreIds = Arrays.asList(cart.getScoreIds().split(","));

            User user = authService.getCurrentUser(userId);
            if (user != null) {

                List<String> ownedScoreIds = user.getOwnedScoreIds();

                ownedScoreIds.addAll(scoreIds);

                user.setOwnedScoreIds(ownedScoreIds);

                authService.updateUser(user);

                cart.setScoreIds(null);
                cartRepository.save(cart);
            }
        }
    }

 
    private String generateUniqueCartId() {
        String cartId;
        do {
            cartId = generateRandomCartId();
        } while (cartRepository.existsById(cartId));
        return cartId;
    }

    private String generateRandomCartId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

}