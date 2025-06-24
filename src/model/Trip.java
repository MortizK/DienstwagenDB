package model;

import util.modelUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Trip {
    private int driverId;
    private int carId;
    private int startKm;
    private int endKm;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;

    public Trip(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid trip term, should in Form: 'X123, X123, 12345, 12345, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss");
        }
        this.driverId = modelUtil.formatId(parts[0], true);
        this.carId = modelUtil.formatId(parts[1], true);
        this.startKm = modelUtil.formatId(parts[2], false);
        this.endKm = modelUtil.formatId(parts[3], false);
        try {
            this.startDateTime = LocalDateTime.parse(parts[4]);
            this.endDateTime = LocalDateTime.parse(parts[5]);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date time format, should in Form: 'yyyy-MM-dd HH:mm:ss'");
        }
    }

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

    public int getCarId() {
        return carId;
    }

    public int getDriverId() {
        return driverId;
    }
}
