package com.alltix.controller;

import com.alltix.model.*;
import com.alltix.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private SeatPricingService seatPricingService;

    // -------------------------------------------------------------------------
    // PUBLIC ACCESS & USER AUTHENTICATION
    // -------------------------------------------------------------------------

    // Homepage
    @GetMapping("/")
    public String home(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "index";
    }

    // About page
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    // Sign Up
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/signin";
    }

    // Login (Sign In)
    @GetMapping("/signin")
    public String signinForm() {
        return "signin";
    }

    @PostMapping("/signin")
    public String signinUser(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<User> userOpt = userService.getUserByEmail(email); // Assuming userService.getUserByEmail or findByEmail exists

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User user = userOpt.get();
            session.setAttribute("user", user);

            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        }

        redirectAttributes.addFlashAttribute("error", "Invalid email or password.");
        return "redirect:/signin";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // -------------------------------------------------------------------------
    // USER AND ADMIN BOOKING FUNCTIONALITY
    // -------------------------------------------------------------------------

    // Booking Page
    @GetMapping("/book/{movieId}")
    @Transactional
    public String bookingPage(@PathVariable Long movieId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in to book tickets.");
            return "redirect:/signin";
        }

        Optional<Movie> movieOpt = movieService.getMovieById(movieId);

        if (movieOpt.isPresent()) {
            Movie movie = movieOpt.get();

            List<Cinema> availableCinemas = movie.getAvailableCinemas();
            if (availableCinemas != null) {
                availableCinemas.removeIf(cinema -> cinema == null);
            }

            model.addAttribute("movie", movie);
            model.addAttribute("seatPrices", seatPricingService.getAllSeatPrices());
            model.addAttribute("cinemas", availableCinemas);

            return "booking";
        }
        return "redirect:/";
    }

    // Booking Submission
    @PostMapping("/book")
    @Transactional
    public String bookTicket(@RequestParam Long movieId,
                             @RequestParam String cinema,
                             @RequestParam String seatType,
                             @RequestParam Integer numberOfTickets,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/signin";

        Optional<Movie> movieOpt = movieService.getMovieById(movieId);

        if (movieOpt.isEmpty() || numberOfTickets == null || numberOfTickets <= 0) {
            redirectAttributes.addFlashAttribute("error", "Invalid selection or ticket quantity.");
            return "redirect:/";
        }

        Movie movie = movieOpt.get();

        int seatsAvailable = Optional.ofNullable(movie.getAvailableSeats()).orElse(0);

        if (numberOfTickets > seatsAvailable) {
            redirectAttributes.addFlashAttribute("error", "Not enough seats available. Available: " + seatsAvailable);
            return "redirect:/book/" + movieId;
        }

        Double pricePerTicket = seatPricingService.getSeatPrice(seatType);
        if (pricePerTicket == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid seat type.");
            return "redirect:/book/" + movieId;
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMovie(movie);
        booking.setCinema(cinema);
        booking.setSeatType(seatType);
        booking.setNumberOfTickets(numberOfTickets);
        booking.setTotalAmount(pricePerTicket * numberOfTickets);
        booking.setBookingTime(LocalDateTime.now());

        Booking savedBooking = bookingService.saveBooking(booking);

        movie.setAvailableSeats(seatsAvailable - numberOfTickets);
        movieService.saveMovie(movie);

        redirectAttributes.addFlashAttribute("success", "Booking successful! Your ID is " + savedBooking.getId());

        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/user/booking/print/" + savedBooking.getId();
    }

    // -------------------------------------------------------------------------
    // USER DASHBOARD & TICKET VIEW
    // -------------------------------------------------------------------------

    @GetMapping("/user/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/signin";

        List<Booking> bookings = bookingService.getBookingsByUser(user);
        model.addAttribute("bookings", bookings);
        model.addAttribute("user", user);

        return "user_dashboard";
    }

    // Print/View Ticket (direct redirect to print-friendly page)
    @GetMapping("/user/booking/print/{id}")
    public String printTicket(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/signin";

        Booking booking = bookingService.getBookingById(id).orElse(null);

        if (booking == null) {
            redirectAttributes.addFlashAttribute("error", "Booking not found.");
            return "redirect:/user/dashboard";
        }

        if (!booking.getUser().getId().equals(user.getId()) && !"ADMIN".equals(user.getRole())) {
            redirectAttributes.addFlashAttribute("error", "You do not have permission to view this ticket.");
            return "redirect:/user/dashboard";
        }

        model.addAttribute("booking", booking);
        model.addAttribute("movie", booking.getMovie());

        return "print_ticket";
    }

    // AJAX ENDPOINT FOR VIEW TICKET MODAL
    @GetMapping("/api/booking/{id}")
    @ResponseBody
    public Booking getBookingApi(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return null;

        Optional<Booking> bookingOpt = bookingService.getBookingById(id);
        if (bookingOpt.isEmpty()) return null;

        Booking booking = bookingOpt.get();

        if (!booking.getUser().getId().equals(user.getId()) && !"ADMIN".equals(user.getRole())) {
            return null;
        }

        return booking;
    }

    // -------------------------------------------------------------------------
    // ADMIN DASHBOARD
    // -------------------------------------------------------------------------

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/signin";
        }

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("users", userService.getAllUsers());

        model.addAttribute("movie", new Movie());
        model.addAttribute("cinema", new Cinema());
        model.addAttribute("cinemas", cinemaService.getAllCinemas());
        model.addAttribute("unlinkedCinemas", java.util.Collections.emptyList());

        return "admin_dashboard";
    }

    // -------------------------------------------------------------------------
    // ADMIN ACTIONS: CINEMA MANAGEMENT
    // -------------------------------------------------------------------------

    @PostMapping("/admin/cinemas/add")
    public String addCinema(@ModelAttribute Cinema cinema, @RequestParam String facilitiesString, RedirectAttributes redirectAttributes) {
        List<String> facilities = Arrays.asList(facilitiesString.split("\\s*,\\s*"));
        cinema.setFacilities(facilities);
        cinemaService.saveCinema(cinema);
        redirectAttributes.addFlashAttribute("success", "Cinema added successfully!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/cinemas/delete/{id}")
    public String deleteCinema(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        cinemaService.deleteCinema(id);
        redirectAttributes.addFlashAttribute("success", "Cinema deleted successfully!");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/cinemas/edit/{id}")
    public String editCinema(@PathVariable Long id, Model model) {
        Optional<Cinema> cinema = cinemaService.getCinemaById(id);
        if (cinema.isPresent()) {
            model.addAttribute("cinema", cinema.get());
            return "edit_cinema";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/cinemas/update")
    public String updateCinema(@ModelAttribute Cinema cinema, @RequestParam String facilitiesString, RedirectAttributes redirectAttributes) {
        List<String> facilities = Arrays.asList(facilitiesString.split("\\s*,\\s*"));
        cinema.setFacilities(facilities);
        cinemaService.saveCinema(cinema);
        redirectAttributes.addFlashAttribute("success", "Cinema updated successfully!");
        return "redirect:/admin/dashboard";
    }

    // -------------------------------------------------------------------------
    // ADMIN ACTIONS: MOVIE MANAGEMENT
    // -------------------------------------------------------------------------

    @PostMapping("/admin/movies/add")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/movies/cinemas/{movieId}")
    public String manageMovieCinemas(@PathVariable Long movieId, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/signin";
        }

        Movie movie = movieService.getMovieById(movieId).orElse(null);
        if (movie == null) {
            redirectAttributes.addFlashAttribute("error", "Movie not found.");
            return "redirect:/admin/dashboard";
        }

        List<Cinema> allCinemas = cinemaService.getAllCinemas();

        model.addAttribute("movie", movie);
        model.addAttribute("allCinemas", allCinemas);

        List<Cinema> unlinkedCinemas = allCinemas.stream()
                .filter(cinema -> !movie.getAvailableCinemas().contains(cinema))
                .collect(Collectors.toList());

        model.addAttribute("unlinkedCinemas", unlinkedCinemas);

        return "movie_cinema_management";
    }

    @PostMapping("/admin/movies/cinemas/add")
    public String addCinemaToMovie(@RequestParam Long movieId, @RequestParam Long cinemaId, RedirectAttributes redirectAttributes) {
        movieService.addCinemaToMovie(movieId, cinemaId);
        redirectAttributes.addFlashAttribute("success", "Cinema successfully linked to movie.");
        return "redirect:/admin/movies/cinemas/" + movieId;
    }

    @GetMapping("/admin/movies/cinemas/remove/{movieId}/{cinemaId}")
    public String removeCinemaFromMovie(@PathVariable Long movieId, @PathVariable Long cinemaId, RedirectAttributes redirectAttributes) {
        movieService.removeCinemaFromMovie(movieId, cinemaId);
        redirectAttributes.addFlashAttribute("success", "Cinema successfully unlinked from movie.");
        return "redirect:/admin/movies/cinemas/" + movieId;
    }

    // -------------------------------------------------------------------------
    // ADMIN ACTIONS: USER/BOOKING MANAGEMENT
    // -------------------------------------------------------------------------

    // ... (Your existing User/Booking View/Edit/Delete methods) ...

    @GetMapping("/admin/users/view/{id}")
    public String viewUserDetails(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        User adminUser = (User) session.getAttribute("user");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            redirectAttributes.addFlashAttribute("error", "Access denied.");
            return "redirect:/signin";
        }

        User userToView = userService.getUserById(id).orElse(null);

        if (userToView == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/admin/dashboard";
        }

        List<Booking> userBookings = bookingService.getUserBookings(userToView);

        model.addAttribute("userToView", userToView);
        model.addAttribute("userBookings", userBookings);

        return "user_details";
    }

    // Admin - EDIT USER FORM (requires user_edit.html)
    @GetMapping("/admin/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found for id: " + id));
        model.addAttribute("user", user);
        return "user_edit";
    }

    // Admin - UPDATE USER
    @PostMapping("/admin/users/update")
    public String updateUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin/dashboard";
    }

    // Admin - Delete User
    @GetMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/dashboard";
    }

    // Admin - EDIT BOOKING FORM (requires booking_edit.html)
    @GetMapping("/admin/bookings/edit/{id}")
    public String editBookingForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found for id: " + id));

        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("users", userService.getAllUsers());

        model.addAttribute("booking", booking);
        return "booking_edit";
    }

    // Admin - UPDATE BOOKING
    @PostMapping("/admin/bookings/update")
    public String updateBooking(@ModelAttribute Booking booking) {
        bookingService.saveBooking(booking);
        return "redirect:/admin/dashboard";
    }

    // Admin - Delete Booking
    @GetMapping("/admin/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/admin/dashboard";
    }
}