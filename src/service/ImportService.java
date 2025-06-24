package service;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportService {
    private final String NEW_ENTITY = "New_Entity:";
    private final String DELIMITER = ",";

    private final List<Trip> trips = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();

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

    private void addTrip(String line) {
        trips.add(new Trip(line, DELIMITER));
    }

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

    private String getType(String header) {
        boolean driverId = header.startsWith("fahrerId");
        boolean carId = header.contains("fahrzeugId");

        if (driverId && carId) {
            return "TRIP";
        } else if (driverId) {
            return "DRIVER";
        } else {
            return "CAR";
        }
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public List<Car> getCars() {
        return cars;
    }
}
