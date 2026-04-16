package com.example.ratingsservice.dto;

public class RatingDto {
    private String movieId;
    private double rating;

    public RatingDto(String movieId, double rating) {
        this.movieId = movieId;
        this.rating = rating;
    }
    // Getters and Setters
    public String getMovieId() { return movieId; }
    public double getRating() { return rating; }
}
