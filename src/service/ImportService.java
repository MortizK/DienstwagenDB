package service;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to import data from a file.<br>
 * This is also the base class for the other services and contains the List of all loaded entities.
 */
public class ImportService {
    private final String NEW_ENTITY = "New_Entity:";
    private final String DELIMITER = ",";

    private final List<Trip> trips = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();

    /**
     * Loads the data from the given file path.<br>
     * The filePath can be a relative path like 'src/file.db' or an absolute path like 'C:/dir/file.db'.<br>
     * @param filePath The path to the file to be loaded. The file must be in the correct format.
     * @return String with the different number of objects loaded.
     * @throws RuntimeException if the file could not be loaded.
     */
    public String loadData(String filePath) {
        StringBuilder result = new StringBuilder();

        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            String type = "";

            while ((line = bf.readLine()) != null) {
                // Check for "New_Entity:" to change the type
                if (line.startsWith(NEW_ENTITY)) {
                    String header = line.substring(NEW_ENTITY.length());
                    type = getType(header);
                    continue; // To Skip this line
                }

                switch (type) {
                    case "TRIP":
                        addTrip(line);
                        break;
                    case "DRIVER":
                        addDriver(line);
                        break;
                    case "CAR":
                        addCar(line);
                        break;
                }
            }

            result.append("Import erfolgreich abgeschlossen!\n");
            result.append("Anzahl Fahrzeuge: ").append(cars.size()).append("\n");
            result.append("Anzahl Fahrer: ").append(drivers.size()).append("\n");
            result.append("Anzahl Fahrten: ").append(trips.size()).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result.toString();
    }

    /**
     * Adds a new Trip to the List of trips.<br>
     * Does not check for duplicates.
     * @param line in Form: 'F001,V001,150000,150100,2024-01-01T14:00:00,2024-01-01T15:00:00'
     * @see model.Trip Trip Class
     */
    private void addTrip(String line) {
        trips.add(new Trip(line, DELIMITER));
    }

    /**
     * Adds a new Driver to the List of drivers.<br>
     * Does check for duplicate IDs. If a duplicate is found, the line is skipped and a message is printed to the console.
     * @param line in Form: 'F001, Sophie, Mueller, BE'
     * @see model.Driver Driver Class
     */
    private void addDriver(String line) {
        Driver newDriver = new Driver(line, DELIMITER);

        // Check for duplicate ID
        boolean duplicate = drivers.stream()
                .anyMatch(driver -> driver.getId() == newDriver.getId());

        if (duplicate) {
            System.out.println("Duplicate driver ID found! Line: '" + line + "' was skipped!");
            return;
        }

        drivers.add(newDriver);
    }

    /**
     * Adds a new Car to the List of cars.<br>
     * Does check for duplicate IDs. If a duplicate is found, the line is skipped and a message is printed to the console.
     * @param line in Form: 'V001, Opel, Corsa, S-BC-4566'
     * @see model.Car Car Class
     */
    private void addCar(String line) {
        Car newCar = new Car(line, DELIMITER);

        // Check for duplicate ID
        boolean duplicate = cars.stream()
                .anyMatch(car -> car.getId() == newCar.getId());

        if (duplicate) {
            System.out.println("Duplicate car ID found! Line: '" + line + "' was skipped!");
            return;
        }

        cars.add(newCar);
    }

    /**
     * Returns the type of the entity based on the given header.
     * <ul>
     *     <li>fahrerId,vorname,nachname,fuehrerscheinklasse -> Driver</li>
     *     <li>fahrzeugId,hersteller,modell,kennzeichen -> Car</li>
     *     <li>fahrerId,fahrzeugId,startKm,endKm,startzeit,endzeit -> Trip</li>
     * </ul>
     * @param header A String with the delimiter ','
     * @return A Type String like "DRIVER", "CAR", "TRIP" or "ERROR" if the header does not match any of the above.
     */
    private String getType(String header) {
        boolean driverId = header.startsWith("fahrerId");
        boolean carId = header.contains("fahrzeugId");

        if (driverId && carId) {
            return "TRIP";
        } else if (driverId) {
            return "DRIVER";
        } else if (carId){
            return "CAR";
        }
        return  "ERROR";
    }

    /**
     * Getter for the List of all loaded trips.
     * @return List of all loaded trips.
     * @see model.Trip Trip Class
     */
    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Getter for the List of all loaded drivers.
     * @return List of all loaded drivers.
     * @see model.Driver Driver Class
     */
    public List<Driver> getDrivers() {
        return drivers;
    }

    /**
     * Getter for the List of all loaded cars.
     * @return List of all loaded cars.
     * @see model.Car Car Class
     */
    public List<Car> getCars() {
        return cars;
    }
}
