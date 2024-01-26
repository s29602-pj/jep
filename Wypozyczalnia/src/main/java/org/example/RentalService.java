package org.example;
import java.time.LocalDate;
import java.util.Optional;

import java.time.temporal.ChronoUnit;

public class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }

    public Rental rent(int userId, String vin, LocalDate startDate, LocalDate endDate) {
        if (!isAvailable(vin, startDate, endDate)) {
            return null;
        }

        Optional<Car> carOptional = carStorage.getCars().stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst();

        if (carOptional.isPresent()) {
            Rental rental = new Rental(new User(userId), carOptional.get(), startDate, endDate);
            rentalStorage.addRental(rental);
            return rental;
        }

        return null;
    }

    public boolean isAvailable(String vin, LocalDate startDate, LocalDate endDate) {
        boolean carExist = carExist(vin).isPresent();

        if (!carExist) {
            return false;
        }

        if (rentalStorage.getRentals().isEmpty()) {
            return true;
        }

        for (Rental rental : rentalStorage.getRentals()) {
            if (rental.getCar().getVin().equals(vin)) {
                if (isOverlappingDate(startDate, endDate, rental)) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        return true;
    }

    public Optional<Car> carExist(String vin) {
        return carStorage.getCars().stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst();
    }

    public double estimatePrice (String vin, LocalDate startDate, LocalDate endDate){
        Car carOptional1 = carExist(vin).orElseThrow();
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        double estimatedValue = 500 * daysBetween;
        return estimatedValue * carOptional1.getType().getMultiplier() * (-1);
    }

    private boolean isOverlappingDate(LocalDate startDate, LocalDate endDate, Rental rental) {
        boolean isEndDateBeforeRentalStart = endDate.isBefore(rental.getStartDate());
        boolean isStartDateAfterRentalEnd = startDate.isAfter(rental.getEndDate());
        return !(isEndDateBeforeRentalStart || isStartDateAfterRentalEnd);
    }ttttttttttttttt
}

