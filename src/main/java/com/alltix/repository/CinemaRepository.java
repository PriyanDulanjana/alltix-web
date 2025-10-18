package com.alltix.repository;

import com.alltix.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
    List<Cinema> findByCity(String city);
    boolean existsByName(String name);
}