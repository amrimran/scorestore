package com.scoreshelf.scorestore.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "musicscores") 
public class MusicScore {

    @Id
    private String id; 

    private String title;
    private String composer;
    private String genre;
    private String emotion;
    private String gender;
    private double price;
    
    public MusicScore() {
    }
   
    public MusicScore(String id, String title, String composer, String genre, String emotion, String gender, double price) {
        this.id = id;
        this.title = title;
        this.composer = composer;
        this.genre = genre;
        this.emotion = emotion;
        this.gender = gender;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    @Override
    public String toString() {
        return "MusicScore{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", composer='" + composer + '\'' +
                ", genre='" + genre + '\'' +
                ", emotion='" + emotion + '\'' +
                ", gender='" + gender + '\'' +
                ", price=" + price +
                '}';
    }
}