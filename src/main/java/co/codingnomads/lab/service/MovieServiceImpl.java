package co.codingnomads.lab.service;

import co.codingnomads.lab.entity.Movie;
import co.codingnomads.lab.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getMoviesByMinimumRating(Double rating) throws IllegalArgumentException{
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must specify a value between 0 and 10");
        }
        return movieRepository.getMoviesByRatingGreaterThanEqual(rating);
    }
}
