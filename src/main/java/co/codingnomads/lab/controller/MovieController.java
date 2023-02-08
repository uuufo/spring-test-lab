package co.codingnomads.lab.controller;

import co.codingnomads.lab.entity.Movie;
import co.codingnomads.lab.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MovieController {

    private MovieService movieService;

    @GetMapping("/all")
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "/rating/{rating}", produces = "application/json")
    public List<Movie> getMoviesByMinimumRating(@PathVariable(name = "rating") Double rating) {
        return movieService.getMoviesByMinimumRating(rating);
    }
}
