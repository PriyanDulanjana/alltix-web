package com.alltix.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class SeatPricingService {

    private final Map<String, Double> seatPrices = new HashMap<>();

    public SeatPricingService() {
        // Initialize seat prices here, which will run when the application starts
        // This initialization ensures prices are present even if DataLoader isn't used.
        seatPrices.put("ODC", 450.0);
        seatPrices.put("BALCONY", 900.0);
        seatPrices.put("PREMIUM", 1200.0);
        seatPrices.put("EXECUTIVE", 1500.0);
    }


    public void saveSeatPrice(String seatType, double price) {
        // Ensure the seat type is stored in uppercase for consistency, matching getSeatPrice logic
        seatPrices.put(seatType.toUpperCase(), price);
    }

    public Double getSeatPrice(String seatType) {
        // The previous default was 500.0. Changed to null to match the check in MainController.
        return seatPrices.get(seatType.toUpperCase());
    }

    public Map<String, Double> getAllSeatPrices() {
        return new HashMap<>(seatPrices);
    }

    public boolean isValidSeatType(String seatType) {
        return seatPrices.containsKey(seatType.toUpperCase());
    }
}