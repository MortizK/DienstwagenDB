package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.DataService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataServiceTests {

    private static final String FILE_PATH = "src/Dienstwagen2025.db";
    private static DataService data;

    @BeforeAll
    static void initAll() {
        data = new DataService();
        data.loadData(FILE_PATH);
    }

    @Test
    void testGetDriverById() {
        assertEquals("Sophie", data.getDriverbyId(1).getName());
        assertEquals("Tom", data.getDriverbyId(2).getName());
        assertEquals("Anna", data.getDriverbyId(10).getName());

        int outOfBoundsId = 51;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                data.getDriverbyId(outOfBoundsId));
        assertEquals("No driver found with id: " + outOfBoundsId, exception.getMessage());
    }

    @Test
    void testGetCarById() {
        assertEquals("S-BC-4566", data.getCarById(1).getNumberPlate());
        assertEquals("S-CD-4737", data.getCarById(2).getNumberPlate());
        assertEquals("S-KL-7613", data.getCarById(10).getNumberPlate());

        int outOfBoundsId = 26;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                data.getCarById(outOfBoundsId));
        assertEquals("No car found with id: " + outOfBoundsId, exception.getMessage());
    }

    @Test
    void testGetTripsByDriver() {
        assertEquals(417, data.getTripsByDriver(1).size());
        assertEquals(382, data.getTripsByDriver(2).size());
        assertEquals(0, data.getTripsByDriver(51).size());
    }

    @Test
    void testGetTripsByCar() {
        assertEquals(934, data.getTripsByCar(1).size());
        assertEquals(910, data.getTripsByCar(2).size());
        assertEquals(0, data.getTripsByCar(26).size());
    }

    @Test
    void testGetDriversByString() {
        assertEquals(4, data.getDriversByString("Anna").size());
        assertEquals(9, data.getDriversByString("Hoff").size());
        assertEquals(0, data.getDriversByString("Thomas").size());
    }

    @Test
    void testGetCarsByString() {
        assertEquals(1, data.getCarsByString("S-BC-4566").size());
        assertEquals(9, data.getCarsByString("Volkswagen").size());
        assertEquals(6, data.getCarsByString("Golf").size());
        assertEquals(0, data.getCarsByString("Porsche").size());
    }

    @Test
    void testGetDriverSpeeding() {
        assertEquals("Anna Becker", data.getDriverSpeeding("S-BC-4566;2024-01-01T19:17:53", ";"));
        assertEquals("Kein Fahrer gefunden", data.getDriverSpeeding("S-BC-4566;2024-01-01T17:17:53", ";"));
        assertEquals("Kein Fahrer gefunden", data.getDriverSpeeding("S-DE-1111;2024-01-01T19:17:53", ";"));

        Exception exception0 = assertThrows(IllegalArgumentException.class, () ->
                data.getDriverSpeeding("2024-01-01T19:17:53", ";"));
        assertEquals("Invalid term, should in Form: 'X123;yyyy-MM-dd HH:mm:ss'", exception0.getMessage());

        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                data.getDriverSpeeding("S-DE-1111;2024-01-35T19:17:53", ";"));
        assertEquals("Invalid date time format, should in Form: 'yyyy-MM-dd HH:mm:ss'", exception1.getMessage());
    }

    @Test
    void testGetDriversOfDay() {
        assertEquals("Ben Wagner (S-GH-3277), Mia Hoffmann (S-GH-3277).", data.getDriversOfDay("F003;2024-08-13", ";"));
        // Alphabetical order
        assertEquals("Anna Hoffmann (S-KL-7613), Lena Hoffmann (S-WX-3158), Max Schneider (S-BC-4566), Mia Fischer (S-BC-4566), Mia Fischer (S-KL-7613), Mia Fischer (S-NO-7724), Mia Fischer (S-NO-7724), Sophie Wagner (S-NO-7724), Tom Mueller (S-BC-4566), Tom Mueller (S-WX-3158), Tom Schmidt (S-NO-7724).", data.getDriversOfDay("F009;2024-01-06", ";"));
        // Did not drive that day
        assertEquals(".", data.getDriversOfDay("F029;2024-01-06", ";"));

        Exception exception0 = assertThrows(IllegalArgumentException.class, () ->
                data.getDriversOfDay("2024-08-13", ";"));
        assertEquals("Invalid term, should in Form: 'X123;yyyy-MM-dd'", exception0.getMessage());

        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                data.getDriversOfDay("F003;2024-08-35", ";"));
        assertEquals("Invalid date format, should in Form: 'yyyy-MM-dd'", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                data.getDriversOfDay("FF03;2024-08-13", ";"));
        assertEquals("Invalid id, should in Form: 'X123'", exception2.getMessage());

    }
}