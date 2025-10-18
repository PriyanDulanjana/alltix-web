package com.alltix.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private String cinema;
    private String seatType;
    private Integer numberOfTickets;
    private Double seatPrice;
    private Double totalAmount;
    private LocalDateTime bookingTime;
    private String status = "CONFIRMED";

    // Constructors
    public Booking() {}

    public Booking(User user, Movie movie, String cinema, String seatType,
                   Integer numberOfTickets, Double seatPrice, Double totalAmount) {
        this.user = user;
        this.movie = movie;
        this.cinema = cinema;
        this.seatType = seatType;
        this.numberOfTickets = numberOfTickets;
        this.seatPrice = seatPrice;
        this.totalAmount = totalAmount;
        this.bookingTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Movie getMovie() { return movie; }
    public void setMovie(Movie movie) { this.movie = movie; }

    public String getCinema() { return cinema; }
    public void setCinema(String cinema) { this.cinema = cinema; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public Integer getNumberOfTickets() { return numberOfTickets; }
    public void setNumberOfTickets(Integer numberOfTickets) { this.numberOfTickets = numberOfTickets; }

    public Double getSeatPrice() { return seatPrice; }
    public void setSeatPrice(Double seatPrice) { this.seatPrice = seatPrice; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}