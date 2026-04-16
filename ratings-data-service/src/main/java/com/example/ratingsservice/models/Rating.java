package com.example.ratingsservice.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity // This annotation indicates that this class is a JPA entity and will be mapped to a database table.
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userId;

    private String movieId;
    private int rating;

    public Rating() {
    }

    public Rating(String movieId, int rating, String userId) {
        this.movieId = movieId;
        this.rating = rating;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
