package com.alltix.config;

import com.alltix.model.Cinema;
import com.alltix.model.Movie;
import com.alltix.model.User;
import com.alltix.service.CinemaService;
import com.alltix.service.MovieService;
import com.alltix.service.SeatPricingService;
import com.alltix.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private SeatPricingService seatPricingService;

    @Override
    public void run(String... args) throws Exception {
        // Create admin user if not exists
        if (userService.getUserByEmail("admin@alltix.com").isEmpty()) {
            User admin = new User("Admin User", "admin@alltix.com", "admin123");
            admin.setRole("ADMIN");
            userService.saveUser(admin);
        }

        // Create regular user if not exists
        if (userService.getUserByEmail("john@example.com").isEmpty()) {
            User user = new User("John Doe", "john@example.com", "password123");
            userService.saveUser(user);
        }

        // ---------------------------------------------------------------------
        // 🚨 FIX: Initialize Seat Pricing Data 🚨
        // Ensures prices are available for booking calculation
        // ---------------------------------------------------------------------
        if (seatPricingService.getAllSeatPrices().isEmpty()) {
            seatPricingService.saveSeatPrice("ODC", 450.0);
            seatPricingService.saveSeatPrice("BALCONY", 900.0);
            seatPricingService.saveSeatPrice("PREMIUM", 1200.0);
            seatPricingService.saveSeatPrice("EXECUTIVE", 1500.0);
        }

        // ---------------------------------------------------------------------
        // Create Indian and Sri Lankan cinemas
        // ---------------------------------------------------------------------
        if (cinemaService.getAllCinemas().isEmpty()) {
            // Indian Cinemas
            Cinema cinema1 = new Cinema("PVR Cinemas", "Phoenix Marketcity", "Mumbai", "022-12345678", 8);
            cinema1.setFacilities(Arrays.asList("IMAX", "4DX", "Dolby Atmos", "Food Court", "Parking"));

            Cinema cinema2 = new Cinema("INOX", "Inorbit Mall", "Mumbai", "022-87654321", 6);
            cinema2.setFacilities(Arrays.asList("Dolby Digital", "3D", "Cafe", "Valet Parking"));

            Cinema cinema3 = new Cinema("Cinepolis", "Viviana Mall", "Thane", "022-11223344", 7);
            cinema3.setFacilities(Arrays.asList("IMAX", "VIP Lounges", "Restaurant", "Kids Zone"));

            Cinema cinema4 = new Cinema("Carnival Cinemas", "Moviestar", "Mumbai", "022-55667788", 5);
            cinema4.setFacilities(Arrays.asList("3D", "Budget Friendly", "Snack Bar"));

            Cinema cinema5 = new Cinema("IMAX Wadala", "Wadala", "Mumbai", "022-99887766", 1);
            cinema5.setFacilities(Arrays.asList("Largest Screen", "Laser Projection", "12-Channel Sound"));

            // Sri Lankan Cinemas
            Cinema cinema6 = new Cinema("LFS", "Liberty Plaza", "Colombo", "+94-112577777", 4);
            cinema6.setFacilities(Arrays.asList("Digital Sound", "Air Conditioned", "Family Seating"));

            Cinema cinema7 = new Cinema("Scope Cinemas", "Colombo City Centre", "Colombo", "+94-117654321", 8);
            cinema7.setFacilities(Arrays.asList("Dolby Atmos", "4K Projection", "Premium Lounges", "Food Court"));

            Cinema cinema8 = new Cinema("PVR SL Kandy", "Kandy City Centre", "Kandy", "+94-812055555", 5);
            cinema8.setFacilities(Arrays.asList("IMAX", "3D", "Snack Bar", "Parking"));

            cinemaService.saveCinema(cinema1);
            cinemaService.saveCinema(cinema2);
            cinemaService.saveCinema(cinema3);
            cinemaService.saveCinema(cinema4);
            cinemaService.saveCinema(cinema5);
            cinemaService.saveCinema(cinema6);
            cinemaService.saveCinema(cinema7);
            cinemaService.saveCinema(cinema8);
        }

        // ---------------------------------------------------------------------
        // Create sample movies
        // ---------------------------------------------------------------------
        if (movieService.getAllMovies().isEmpty()) {
            // Get cinemas (Including the new ones for SL)
            Cinema pvr = cinemaService.getCinemaByName("PVR Cinemas").orElse(null);
            Cinema inox = cinemaService.getCinemaByName("INOX").orElse(null);
            Cinema cinepolis = cinemaService.getCinemaByName("Cinepolis").orElse(null);
            Cinema carnival = cinemaService.getCinemaByName("Carnival Cinemas").orElse(null);
            Cinema lfs = cinemaService.getCinemaByName("LFS").orElse(null);
            Cinema scope = cinemaService.getCinemaByName("Scope Cinemas").orElse(null);
            Cinema pvrKandy = cinemaService.getCinemaByName("PVR SL Kandy").orElse(null);

            // Re-use or fetch IMAX Wadala
            Cinema imaxWadala = cinemaService.getCinemaByName("IMAX Wadala").orElse(null);


            // Existing Movie 1 (linked to SL cinemas)
            Movie movie1 = new Movie(
                    "Avengers: Endgame",
                    "Action",
                    "3h 1m",
                    "English",
                    "***Avengers: Endgame*** delivers the culmination of the entire Marvel Cinematic Universe story up to that point, picking up five years after Thanos wiped out half of all life in the universe. Devastated but determined, the surviving **Avengers**—including Iron Man, Captain America, Thor, Hulk, and Black Widow—devise a risky plan using **Ant-Man's** knowledge of the **Quantum Realm** to execute a **\"Time Heist,\"** traveling to past MCU events to collect the six **Infinity Stones**. They successfully gather the Stones and use them to reverse Thanos's snap, but the action inadvertently brings a past version of Thanos and his massive army into the present, leading to a sprawling, **all-out final battle** for the fate of reality and serving as an emotional conclusion and farewell to several of the franchise's founding heroes.",
                    "/images/avengers.jpg",
                    LocalDateTime.now().plusDays(1).withHour(19).withMinute(0)
            );
            movie1.setAvailableSeats(100);
            if (pvr != null) movie1.addCinema(pvr);
            if (inox != null) movie1.addCinema(inox);
            if (cinepolis != null) movie1.addCinema(cinepolis);
            // Link to Sri Lankan cinemas
            if (lfs != null) movie1.addCinema(lfs);
            if (scope != null) movie1.addCinema(scope);


            // Existing Movie 2
            Movie movie2 = new Movie(
                    "The Batman",
                    "Action",
                    "2h 56m",
                    "English",
                    "The Dark Knight of Gotham City.",
                    "/images/batman.jpg",
                    LocalDateTime.now().plusDays(2).withHour(20).withMinute(30)
            );
            movie2.setAvailableSeats(80);
            if (pvr != null) movie2.addCinema(pvr);
            if (imaxWadala != null) movie2.addCinema(imaxWadala);
            if (cinepolis != null) movie2.addCinema(cinepolis);
            // Link to Sri Lankan cinemas
            if (scope != null) movie2.addCinema(scope);
            if (pvrKandy != null) movie2.addCinema(pvrKandy);


            // Existing Movie 3
            Movie movie3 = new Movie(
                    "Spider-Man: No Way Home",
                    "Action",
                    "2h 28m",
                    "English",
                    "The multiverse unleashed.",
                    "/images/spiderman.jpg",
                    LocalDateTime.now().plusDays(3).withHour(18).withMinute(0)
            );
            movie3.setAvailableSeats(120);
            if (inox != null) movie3.addCinema(inox);
            if (carnival != null) movie3.addCinema(carnival);
            if (imaxWadala != null) movie3.addCinema(imaxWadala);
            // Link to Sri Lankan cinemas
            if (lfs != null) movie3.addCinema(lfs);


            // New Movie 4: Dune: Part Two (Linked to SL and India)
            Movie movie4 = new Movie(
                    "Dune: Part Two",
                    "Sci-Fi",
                    "2h 46m",
                    "English",
                    "Paul Atreides unites with the Fremen to wage war against House Harkonnen.",
                    "/images/dune2.jpg",
                    LocalDateTime.now().plusDays(4).withHour(16).withMinute(30)
            );
            movie4.setAvailableSeats(95);
            if (imaxWadala != null) movie4.addCinema(imaxWadala);
            if (scope != null) movie4.addCinema(scope);
            if (pvrKandy != null) movie4.addCinema(pvrKandy);


            // New Movie 5: Jawan (Action, Indian Language)
            Movie movie5 = new Movie(
                    "Jawan",
                    "Action",
                    "2h 49m",
                    "Hindi",
                    "A high-octane action thriller about a man set on correcting the wrongs in society.",
                    "/images/jawan.jpg",
                    LocalDateTime.now().plusDays(5).withHour(21).withMinute(0)
            );
            movie5.setAvailableSeats(150);
            if (pvr != null) movie5.addCinema(pvr);
            if (inox != null) movie5.addCinema(inox);
            if (lfs != null) movie5.addCinema(lfs);
            if (carnival != null) movie5.addCinema(carnival);


            movieService.saveMovie(movie1);
            movieService.saveMovie(movie2);
            movieService.saveMovie(movie3);
            movieService.saveMovie(movie4); // Save new movies
            movieService.saveMovie(movie5);
        }
    }
}