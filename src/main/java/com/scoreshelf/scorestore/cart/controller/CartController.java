package com.scoreshelf.scorestore.cart.controller;

import com.scoreshelf.scorestore.auth.service.AuthServiceImpl;
import com.scoreshelf.scorestore.base.entity.Cart;
import com.scoreshelf.scorestore.base.entity.MusicScore;
import com.scoreshelf.scorestore.base.entity.User;
import com.scoreshelf.scorestore.base.service.ICartService;
import com.scoreshelf.scorestore.musicscore.service.MusicScoreServiceImpl;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private MusicScoreServiceImpl musicScoreService;

    @GetMapping("/customer-cart")
    public String customerCart(Model model, HttpSession session) {

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        Cart cart = cartService.getCartByUserId(currentUser.getId());

        List<MusicScore> cartScores = List.of();
        if (cart != null && cart.getScoreIds() != null && !cart.getScoreIds().isEmpty()) {
            List<String> scoreIds = Arrays.asList(cart.getScoreIds().split(","));
            cartScores = musicScoreService.getMusicScoresByIds(scoreIds);
        }

        model.addAttribute("cart", cart != null ? cart : new Cart());
        model.addAttribute("cartScores", cartScores);

        return "customer/customer-cart";
    }

     @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String scoreId, HttpSession session) {
        
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        if (username == null || !"customer".equals(role)) {
            return "redirect:/login"; 
        }
 
        User currentUser = authService.getCurrentUser(username);
  
        cartService.addScoreToCart(currentUser.getId(), scoreId);

        return "redirect:/customer-homepage"; 
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String scoreId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to remove items from the cart.");
            return "redirect:/login";
        }

        User currentUser = authService.getCurrentUser(username);
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/login";
        }

        cartService.removeScoreFromCart(currentUser.getId(), scoreId);

        redirectAttributes.addFlashAttribute("success", "Item removed from cart successfully!");
        return "redirect:/customer-cart";
    }

    @PostMapping("/checkout")
    public String checkoutCart(HttpSession session, RedirectAttributes redirectAttributes) {

        String username = (String) session.getAttribute("username");

        if (username == null) {

            redirectAttributes.addFlashAttribute("error", "You must be logged in to checkout.");
            return "redirect:/login";
        }

        try {

            User currentUser = authService.getCurrentUser(username);
            if (currentUser == null) {

                redirectAttributes.addFlashAttribute("error", "User not found.");
                return "redirect:/login";
            }

            Cart cart = cartService.getCartByUserId(currentUser.getId());
            if (cart == null || cart.getScoreIds() == null || cart.getScoreIds().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Your cart is empty.");
                return "redirect:/customer-cart";
            }

            List<String> scoreIds = Arrays.asList(cart.getScoreIds().split(","));

            List<String> ownedScoreIds = new ArrayList<>(currentUser.getOwnedScoreIds());

            ownedScoreIds.addAll(scoreIds);

            currentUser.setOwnedScoreIds(ownedScoreIds);

            authService.updateUser(currentUser);

            cart.setScoreIds(null);
            cartService.saveCart(cart);

            redirectAttributes.addFlashAttribute("success", "Checkout successful!");

            return "redirect:/customer-library";

        } catch (Exception e) {

            System.err.println("Error during checkout: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "An error occurred during checkout. Please try again.");
            return "redirect:/customer-cart";
        }
    }
}