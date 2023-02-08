package co.codingnomads.lab.repository;

import co.codingnomads.lab.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> getMoviesByRatingGreaterThanEqual(Double rating);
}
