package com.scoreshelf.scorestore.base.entity;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private String role;
    private String username;
    private String owned_score_ids;

    public User() {
        this.id = generateRandomId();
    }
    
    private String generateRandomId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getOwnedScoreIds() {
        if (owned_score_ids == null || owned_score_ids.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(owned_score_ids.split(","));
    }

    public void setOwnedScoreIds(List<String> scoreIds) {
        if (scoreIds == null || scoreIds.isEmpty()) {
            this.owned_score_ids = null; 
        } else {
            this.owned_score_ids = String.join(",", scoreIds); 
        }
    }
}