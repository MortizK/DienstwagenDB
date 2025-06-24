package service;

import model.*;
import util.modelUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    public List<Car> getCarsByString(String searchTerm) {
        return getCars().stream()
                .filter(car ->
                        car.getBrand().contains(searchTerm) ||
                        car.getModel().contains(searchTerm) ||
                        car.getNumberPlate().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Car> getCarsByNumberPlate(String numberPlate) {
        return getCars().stream()
                .filter(car -> car.getNumberPlate().equals(numberPlate))
                .collect(Collectors.toList());
    }

    public List<Driver> getDriversByString(String searchTerm) {
        return getDrivers().stream()
                .filter( driver -> driver.getFullName().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public Driver getDriverbyId(int driverId) {
        return getDrivers().stream()
                .filter(car -> car.getId() == driverId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No driver found with id: " + driverId));
    }

    private List<Trip> getTripsOnDay(LocalDate date) {
        return getTrips().stream()
                .filter(trip -> trip.isOnDay(date))
                .collect(Collectors.toList());
    }

    public String getDriverSpeeding(String term, String delimiter) {
        String[] parts = term.split(delimiter);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid term, should in Form: 'X123;yyyy-MM-dd HH:mm:ss'");
        }

        String numberPlate = parts[0];
        LocalDateTime time;
        try {
            time = LocalDateTime.parse(parts[1]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date time format, should in Form: 'yyyy-MM-dd HH:mm:ss'");
        }

        List<Car> car = getCarsByNumberPlate(numberPlate);

        // No car found
        if (car.isEmpty()) {
            return "Kein Fahrer gefunden";
        }

        int carId = car.get(0).getId();

        List<Trip> trips = getTripsByCar(carId).stream()
                .filter(trip -> trip.isInTime(time))
                .collect(Collectors.toList());

        // No trip found in the timeframe
        if (trips.isEmpty()) {
            return "Kein Fahrer gefunden";
        }

        int driverId = trips.get(0).getDriverId();

        return getDriverbyId(driverId).getFullName();
    }

    public String getDriversOfDay(String term, String delimiter) {
        String[] parts = term.split(delimiter);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid term, should in Form: 'X123;yyyy-MM-dd'");
        }

        int driverId = modelUtil.formatId(parts[0], true);
        LocalDate date;
        try {
            date = LocalDate.parse(parts[1]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format, should in Form: 'yyyy-MM-dd'");
        }

        List<Trip> trips = getTripsOnDay(date);

        // Find the cars which the driver has driven that day
        List<Integer> carIds = new ArrayList<>();
        trips.stream()
                .filter(trip -> trip.getDriverId() == driverId)
                .forEach(trip -> carIds.add(trip.getCarId()));

        trips = trips.stream()
                .filter(trip -> carIds.contains(trip.getCarId()))
                .collect(Collectors.toList());

        // List all 'unique' driver and car trips as an Integer for easy filtering
        List<Integer> uniqueIds = new ArrayList<>();

        // Collect all Result Strings to be sorted later
        List<String> driverWithNumberplate = new ArrayList<>();

        trips.forEach(trip -> {
           int driverIdTrip = trip.getDriverId();
           int carIdTrip = trip.getCarId();
           int uniqueId = driverIdTrip * 1000 + carIdTrip;

           if (!uniqueIds.contains(uniqueId) && driverId != driverIdTrip) {
               uniqueIds.add(uniqueId);
               String fullNames = getDriverbyId(driverIdTrip).getFullName();
               String numberPlate = getCarById(carIdTrip).getNumberPlate();
               driverWithNumberplate.add(fullNames + " (" + numberPlate + ")");
           }
        });

        // Sort driverWithNumberplate alphabetically
        driverWithNumberplate.sort(String::compareTo);

        return driverWithNumberplate.toString().replace("[", "").replace("]", ".");
    }
}
