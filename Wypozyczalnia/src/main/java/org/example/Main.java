package org.example;


import java.time.LocalDate;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        RentalStorage rentalStorage = RentalStorage.getInstance();
        CarStorage carStorage = CarStorage.getInstance();

        Car car1 = new Car("Vw", "Passat","vin1",Type.STANDARD);
        Car car2 = new Car("Vw", "Golf","vin2",Type.PREMIUM);
        Car car3 = new Car("Skoda", "Octavia","vin3",Type.STANDARD);
        Car car4 = new Car("Skoda", "Fabia","vin4",Type.PREMIUM);

        carStorage.addCar(car1);
        carStorage.addCar(car2);
        carStorage.addCar(car3);
        carStorage.addCar(car4);

        RentalService rentalService = new RentalService(carStorage, rentalStorage);

        Rental rental = rentalService.rent(1, "vin1", LocalDate.now().minusDays(25), LocalDate.now().minusDays(7));
        System.out.println(rental);

        boolean isAvailable1 = rentalService.isAvailable("vin1", LocalDate.now().plusDays(3), LocalDate.now().plusDays(1));
        System.out.println(isAvailable1);

        Optional<Car> carExistOptional = rentalService.carExist("vin1");
        boolean carExist = carExistOptional.isPresent();
        System.out.println(carExist);


        double estimatedValue = rentalService.estimatePrice("vin1", LocalDate.now().plusDays(25), LocalDate.now().plusDays(7));
        System.out.println("Przewidywana cena: " + estimatedValue);
    }

}