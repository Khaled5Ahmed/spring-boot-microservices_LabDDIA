package com.example.movieinfoservice.resources; // 1. Use your actual folder path

import com.example.movieinfoservice.models.Movie; // 2. Import from your local model
import com.example.movieinfoservice.repositories.MovieRepository; // 3. Import from your local repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Optional; 

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Autowired 
    private MovieRepository movieRepository;

    @Value("${api.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    public MovieResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        Optional<Movie> cachedMovie = movieRepository.findById(movieId);
        if (cachedMovie.isPresent()) {
            return cachedMovie.get();
        }

        Movie movie = new Movie(movieId, "Generated Name " + movieId, "Description");

        try {
            Thread.sleep(200); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        movieRepository.save(movie);
        return movie;
    }
}