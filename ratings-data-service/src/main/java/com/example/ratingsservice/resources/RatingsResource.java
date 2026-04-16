package com.example.ratingsservice.resources;

import com.example.ratingsservice.models.Rating;
import com.example.ratingsservice.models.UserRating;
import com.example.ratingsservice.dto.RatingDto;
import com.example.ratingsservice.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

    @Autowired
    private RatingRepository ratingRepository;


    @RequestMapping("/{userId}")
    public UserRating getRatingsOfUser(@PathVariable String userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        return new UserRating(ratings);
    }


    @GetMapping("/top")
    public List<RatingDto> getTopMovies() {

        List<Object[]> results = ratingRepository.findTopRatedMovies(PageRequest.of(0, 10));


        return results.stream()
                .map(res -> new RatingDto((String) res[0], (Double) res[1]))
                .collect(Collectors.toList());
    }
}
