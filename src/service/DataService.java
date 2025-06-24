package service;

import model.*;

import java.util.List;
import java.util.stream.Collectors;

public class DataService extends ImportService {
    public List<Trip> getTripsByDriver(int driverId) {
        return getTrips().stream()
                .filter(trip -> trip.getDriverId() == driverId)
                .collect(Collectors.toList());
    }

    public List<Trip> getTripsByCar(int carId) {
        return getTrips().stream()
                .filter(trip -> trip.getCarId() == carId)
                .collect(Collectors.toList());
    }

    public Car getCarById(int carId) {
        return getCars().stream()
                .filter(car -> car.getId() == carId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No car found with id: " + carId));
    }

    public Driver getDriverbyId(int driverId) {
        return getDrivers().stream()
                .filter(car -> car.getId() == driverId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No driver found with id: " + driverId));
    }

    //    public List<Car> getCarsDrivenBy(int driverId) {
    //        List<Car> cars = new ArrayList<>();
    //        getTripsByDriver(driverId)
    //                .forEach(trip -> {
    //                    cars.add(getCarById(trip.getCarId()));
    //                });
    //        return cars;
    //    }
    //
    //    public List<Driver> getDriversWhoDrove(int carId) {
    //        List<Driver> drivers = new ArrayList<>();
    //        getTripsByCar(carId)
    //                .forEach(trip -> {
    //                    drivers.add(getDriverbyId(trip.getDriverId()));
    //                });
    //        return drivers;
    //    }
}
