package com.example.ratingsservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.example.ratingsservice.repositories.RatingRepository;

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
class RatingsDataServiceApplicationTests {

    @MockBean
    private RatingRepository ratingRepository;

    @Test
    void contextLoads() {
    }
}