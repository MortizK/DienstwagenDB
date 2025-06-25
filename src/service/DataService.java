package service;

import model.*;
import util.modelUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to provide data from the loaded entities.<br>
 * This class extends the ImportService and adds some methods to filter and combine data from the loaded entities.
 * @see ImportService
 */
public class DataService extends ImportService {
    /**
     * Returns a List of all loaded Trips where the driverId matches<br>
     * @param driverId of the driver to be searched for.
     * @return List of Trips where the driverId matches.
     * @see model.Trip Trip Class
     * @see model.Driver Driver Class
     * @see List List Class
     */
    public List<Trip> getTripsByDriver(int driverId) {
        return getTrips().stream()
                .filter(trip -> trip.getDriverId() == driverId)
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of all loaded Trips where the carId matches<br>
     * @param carId of the car to be searched for.
     * @return List of Trips where the carId matches.
     * @see model.Trip Trip Class
     * @see model.Car Car Class
     * @see List List Class
     */
    public List<Trip> getTripsByCar(int carId) {
        return getTrips().stream()
                .filter(trip -> trip.getCarId() == carId)
                .collect(Collectors.toList());
    }

    /**
     * Returns a Car object with the given carId.
     * @param carId of the car to be searched for.
     * @return Car object with the given carId.
     * @throws IllegalArgumentException if no car was found with the given carId.
     * @see model.Car Car Class
     */
    public Car getCarById(int carId) {
        return getCars().stream()
                .filter(car -> car.getId() == carId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No car found with id: " + carId));
    }

    /**
     * Returns a List of all loaded Cars where the brand, model or numberPlate contains the given searchTerm.
     * @param searchTerm String to search.
     * @return List of Cars where the searchTerm matches.
     * @see model.Car Car Class
     * @see List List Class
     */
    public List<Car> getCarsByString(String searchTerm) {
        return getCars().stream()
                .filter(car ->
                        car.getBrand().contains(searchTerm) ||
                        car.getModel().contains(searchTerm) ||
                        car.getNumberPlate().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of all loaded Cars where the numberPlate matches the given numberPlate.
     * @param numberPlate Example: 'S-BC-4566'
     * @return List of Cars where the numberPlates match.
     * @see model.Car Car Class
     * @see List List Class
     */
    public List<Car> getCarsByNumberPlate(String numberPlate) {
        return getCars().stream()
                .filter(car -> car.getNumberPlate().equals(numberPlate))
                .collect(Collectors.toList());
    }

    /**
     * Returns a List of all loaded Drivers where the Name contains the given searchTerm.
     * @param searchTerm String to search.
     * @return List of Drivers where the searchTerm matches.
     * @see model.Driver Driver Class
     * @see List List Class
     */
    public List<Driver> getDriversByString(String searchTerm) {
        return getDrivers().stream()
                .filter( driver -> driver.getFullName().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Returns a Driver object with the given driverId.
     * @param driverId of the driver to be searched for.
     * @return Driver object with the given driverId.
     * @throws IllegalArgumentException if no driver was found with the given driverId.
     * @see model.Driver Driver Class
     */
    public Driver getDriverById(int driverId) {
        return getDrivers().stream()
                .filter(car -> car.getId() == driverId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No driver found with id: " + driverId));
    }

    /**
     * Get a List of all loaded Trips where date is on the start or end date of the trip.
     * @param date in Form: 'yyyy-MM-dd'
     * @return List of Trips for that date.
     * @see model.Trip Trip Class
     * @see List List Class
     */
    private List<Trip> getTripsOnDay(LocalDate date) {
        return getTrips().stream()
                .filter(trip -> trip.isOnDay(date))
                .collect(Collectors.toList());
    }

    /**
     * <b>Feature 1.</b><br>
     * This searches for a driver who drove the car with the given numberPlate during the given time.<br>
     * If no driver was found, the method returns 'Kein Fahrer gefunden'.
     * @param term in Form: 'S-XX-1234;yyyy-MM-dd HH:mm:ss'
     * @param delimiter mostly ';'
     * @return String in Form: 'Name Surname' or 'Kein Fahrer gefunden' if no driver was found.
     * @throws IllegalArgumentException if the term is not in the correct form: 'S-XX-1234;yyyy-MM-dd HH:mm:ss'.
     * @throws IllegalArgumentException if the date time format is not in the correct form: 'yyyy-MM-dd HH:mm:ss'.
     */
    public String getDriverSpeeding(String term, String delimiter) {
        String[] parts = term.split(delimiter);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid term, should in Form: 'S-XX-1234;yyyy-MM-dd HH:mm:ss'");
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

        return getDriverById(driverId).getFullName();
    }

    /**
     * <b>Feature 2.</b><br>
     * This searches for all drivers who drove the same car as yourself on the given date.<br>
     * The Drivers are sorted alphabetically.<br>
     * If no other driver was found, the method returns an empty String "".
     * @param term in Form: 'F123;yyyy-MM-dd'
     * @param delimiter mostly ';'
     * @return A Formated String: 'Name Surname (NumberPlate), Name Surname (NumberPlate), ...'
     * @throws IllegalArgumentException if the term is not in the correct form: 'F123;yyyy-MM-dd'.
     * @throws IllegalArgumentException if the date format is not in the correct form: 'yyyy-MM-dd'.
     */
    public String getDriversOfDay(String term, String delimiter) {
        String[] parts = term.split(delimiter);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid term, should in Form: 'F123;yyyy-MM-dd'");
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
               String fullNames = getDriverById(driverIdTrip).getFullName();
               String numberPlate = getCarById(carIdTrip).getNumberPlate();
               driverWithNumberplate.add(fullNames + " (" + numberPlate + ")");
           }
        });

        // Sort driverWithNumberplate alphabetically
        driverWithNumberplate.sort(String::compareTo);

        return driverWithNumberplate.toString().replace("[", "").replace("]", "");
    }
}
