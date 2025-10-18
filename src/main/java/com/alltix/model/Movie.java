package com.alltix.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String genre;
    private String duration;
    private String language;

    @Column(length = 1000)
    private String description;

    private String posterUrl;

    // 🌟 FIX: Initialized to current time to pass @NotNull validation on creation 🌟
    @NotNull(message = "Show time is required")
    private LocalDateTime showTime = LocalDateTime.now();

    private Double ticketPrice;
    private Integer availableSeats;


    // 🌟 FIX: Added (fetch = FetchType.EAGER) for reliable cinema loading 🌟
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_cinemas",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "cinema_id")
    )
    private List<Cinema> availableCinemas = new ArrayList<>();

    // Constructors
    public Movie() {
        this.showTime = LocalDateTime.now(); // Ensure default object is valid
    }

    public Movie(String title, String genre, String duration, String language,
                 String description, String posterUrl, LocalDateTime showTime) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.description = description;
        this.posterUrl = posterUrl;
        this.showTime = showTime;
    }

    // Getters and Setters

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    public LocalDateTime getShowTime() { return showTime; }
    public void setShowTime(LocalDateTime showTime) { this.showTime = showTime; }

    public List<Cinema> getAvailableCinemas() { return availableCinemas; }
    public void setAvailableCinemas(List<Cinema> availableCinemas) { this.availableCinemas = availableCinemas; }

    public void addCinema(Cinema cinema) {
        this.availableCinemas.add(cinema);
    }

    public void removeCinema(Cinema cinema) {
        this.availableCinemas.remove(cinema);
    }
}