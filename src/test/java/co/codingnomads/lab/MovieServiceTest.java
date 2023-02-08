package co.codingnomads.lab;

import co.codingnomads.lab.entity.Movie;
import co.codingnomads.lab.repository.MovieRepository;
import co.codingnomads.lab.service.MovieService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = SpringTestLab.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    @Order(1)
    public void testGetAllMoviesSuccess() {
        List<Movie> movies = movieService.getAllMovies();
        assertThat(movies.size()).isEqualTo(2);
        assertThat(movies.get(0).getName()).isEqualTo("The Shawshank Redemption");
        assertThat(movies.get(0).getRating()).isEqualTo(9.3);
        assertThat(movies.get(1).getName()).isEqualTo("The Pursuit of Happyness");
        assertThat(movies.get(1).getRating()).isEqualTo(8.0);
    }

    @Test
    @Order(2)
    public void testGetMoviesByMinimumRatingSuccess() {
        List<Movie> movies = movieService.getMoviesByMinimumRating(9.0);
        assertThat(movies.size()).isEqualTo(1);
        assertThat(movies.get(0).getName()).isEqualTo("The Shawshank Redemption");
        assertThat(movies.get(0).getRating()).isEqualTo(9.3);
    }

    @Test
    @Order(3)
    public void testGetMoviesByMinimumRatingFailure() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            movieService.getMoviesByMinimumRating(-1d)
        );

        assertThat(exception.getMessage()).isEqualTo("Rating must specify a value between 0 and 10");
    }

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Order(4)
    public void testGetAllMoviesFailure() {

        movieRepository.deleteAll();

        List<Movie> movies = movieService.getAllMovies();
        assertThat(movies.size()).isEqualTo(0);
    }
}
