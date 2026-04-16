package com.example.ratingsservice.repositories;

import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.Query;
import com.example.ratingsservice.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository  // This annotation indicates that this interface is a Spring Data repository, which will be automatically implemented by Spring Data JPA.
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    
    // SELECT * FROM rating WHERE user_id = ?
    List<Rating> findByUserId(String userId);
    
    @Query("SELECT r.movieId as movieId, AVG(r.rating) as avgRating " +
           "FROM Rating r GROUP BY r.movieId ORDER BY avgRating DESC")
    List<Object[]> findTopRatedMovies(Pageable pageable);
}
