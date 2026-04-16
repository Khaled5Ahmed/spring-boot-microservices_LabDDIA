package com.moviecatalogservice.models;

public class TrendingMovie {

    private String movieId;
    private double rating;

    public TrendingMovie(String movieId, double rating) {
        this.movieId = movieId;
        this.rating = rating;
    }

    public String getMovieId() {
        return movieId;
    }

    public double getRating() {
        return rating;
    }
}