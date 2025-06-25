package model;

import util.modelUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Class to represent a Trip.
 */
public class Trip {
    private int driverId;
    private int carId;
    private int startKm;
    private int endKm;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    /**
     * Constructor for Trip class with a term in the form: 'driver, car, startKm, endKm, startDateTime, endDateTime'
     * @param term in the form: 'F001, V001, 150000, 150100, 2024-01-01T14:00:00, 2024-01-01T15:00:00'
     * @param delimiter mostly ','
     * @throws IllegalArgumentException if the term is not in the correct form
     */
    public Trip(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid trip term, should in Form: 'F001,V001,150000,150100,2024-01-01T14:00:00,2024-01-01T15:00:00'");
        }
        this.driverId = modelUtil.formatId(parts[0], true);
        this.carId = modelUtil.formatId(parts[1], true);
        this.startKm = modelUtil.formatId(parts[2], false);
        this.endKm = modelUtil.formatId(parts[3], false);
        try {
            this.startDateTime = LocalDateTime.parse(parts[4]);
            this.endDateTime = LocalDateTime.parse(parts[5]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date time format, should in Form: '2024-01-01T14:00:00'");
        }
    }

    /**
     * Returns true if the Trip was during the given time.
     * @param dateTime in Form: 'yyyy-MM-dd HH:mm:ss'
     * @return true or false
     */
    public boolean isInTime(LocalDateTime dateTime) {
        return dateTime.isAfter(startDateTime) && dateTime.isBefore(endDateTime);
    }

    /**
     * Return true if the Trip started or ended on the given date.<br>
     * Since the Trips are business-Trips, the start and end date can't be more than 1 day apart.
     * @param date in Form: 'yyyy-MM-dd'
     * @return true or false
     */
    public boolean isOnDay(LocalDate date) {
        return date.isEqual(startDateTime.toLocalDate()) ||
                date.isEqual(endDateTime.toLocalDate());
    }

    /**
     * Returns a String representation of the Trip object.<br>
     * Of Form: 'model.Trip@[driverId=DriverId , carId=CarId, startKm=StartKm, endKm=EndKm, startDateTime=StartDateTime, endDateTime=EndDateTime]'
     * @return String
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "@[driverId=" + driverId +
                ", carId=" + carId +
                ", startKm=" + startKm +
                ", endKm=" + endKm +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime + "]";
    }

    /**
     * Getter of the carId attribute.
     * @return carId of the trip.
     */
    public int getCarId() {
        return carId;
    }

    /**
     * Getter of the driverId attribute.
     * @return driverId of the trip.
     */
    public int getDriverId() {
        return driverId;
    }
}
