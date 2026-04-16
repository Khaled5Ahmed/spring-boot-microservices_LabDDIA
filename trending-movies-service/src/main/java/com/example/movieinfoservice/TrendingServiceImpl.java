package com.example.movieinfoservice;

import io.grpc.stub.StreamObserver;
import trending.*;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class TrendingServiceImpl extends TrendingServiceGrpc.TrendingServiceImplBase {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void getTopMovies(Empty request, StreamObserver<MovieList> responseObserver) {
        
         
        Rating[] topRatings = restTemplate.getForObject(
            "http://localhost:8085/ratings/top", 
            Rating[].class
        );

        MovieList.Builder responseBuilder = MovieList.newBuilder();

        if (topRatings != null) {
            Arrays.stream(topRatings).forEach(r -> {
                responseBuilder.addMovies(
                    Movie.newBuilder()
                        .setMovieId(r.getMovieId())
                        .setRating(r.getRating())
                        .build()
                );
            });
        }

       
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();

        System.out.println("Successfully fetched Top 10 movies via SQL Aggregation!");
    }
}



