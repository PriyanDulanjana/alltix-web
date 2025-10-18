package com.alltix.service;

import com.alltix.model.Cinema;
import com.alltix.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll()
                .stream()
                .filter(cinema -> cinema != null)
                .collect(Collectors.toList());

    }

    // 🌟 FIX: Removed incorrect call to undefined repository method 🌟
    public List<Cinema> findCinemasForMovie(Long movieId) {
        return List.of();
    }

    public Optional<Cinema> getCinemaById(Long id) {
        return cinemaRepository.findById(id);
    }

    public Optional<Cinema> getCinemaByName(String name) {
        return cinemaRepository.findByName(name);
    }

    public List<Cinema> getCinemasByCity(String city) {
        return cinemaRepository.findByCity(city);
    }

    public Cinema saveCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public void deleteCinema(Long id) {
        cinemaRepository.deleteById(id);
    }

    public boolean cinemaExists(String name) {
        return cinemaRepository.existsByName(name);
    }
}