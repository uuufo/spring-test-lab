package co.codingnomads.lab.service;

import co.codingnomads.lab.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getMoviesByMinimumRating(Double rating) throws IllegalArgumentException;
}
