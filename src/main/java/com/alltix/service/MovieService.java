package com.alltix.service;

import com.alltix.model.Movie;
import com.alltix.model.Cinema;
import com.alltix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CinemaService cinemaService;


    public List<Movie> getAllMovies() {
        return movieRepository.findByOrderByShowTimeAsc();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }public void addCinemaToMovie(Long movieId, Long cinemaId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        Optional<Cinema> cinemaOpt = cinemaService.getCinemaById(cinemaId);

        if (movieOpt.isPresent() && cinemaOpt.isPresent()) {
            Movie movie = movieOpt.get();
            Cinema cinema = cinemaOpt.get();

            if (!movie.getAvailableCinemas().contains(cinema)) {
                movie.addCinema(cinema);
                movieRepository.save(movie);
            }
        }
    }

    public void removeCinemaFromMovie(Long movieId, Long cinemaId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        Optional<Cinema> cinemaOpt = cinemaService.getCinemaById(cinemaId);

        if (movieOpt.isPresent() && cinemaOpt.isPresent()) {
            Movie movie = movieOpt.get();
            Cinema cinema = cinemaOpt.get();
            movie.removeCinema(cinema);
            movieRepository.save(movie);
        }
    }
}
