package com.scoreshelf.scorestore.base.entity;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "carts") 
public class Cart {
    @Id
    @Column(name = "cart_id") 
    private String cartId; 

    @Column(name = "user_id") 
    private String userId; 

    @Column(name = "score_ids") 
    private String scoreIds; 

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScoreIds() {
        return scoreIds;
    }

    public void setScoreIds(String scoreIds) {
        this.scoreIds = scoreIds;
    }

    
    public List<String> getScoreIdsList() {
        if (scoreIds == null || scoreIds.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(scoreIds.split(","));
    }
}