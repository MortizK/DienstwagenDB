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
}
