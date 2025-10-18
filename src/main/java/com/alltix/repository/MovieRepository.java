package com.alltix.repository;

import com.alltix.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByOrderByShowTimeAsc();
}