package tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ImportService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImportServiceTests {
    private static final String FILE_PATH = "src/Dienstwagen2025.db";
    private static ImportService data;

    @BeforeEach
    void init() {
        data = new ImportService();
    }

    @Test
    void testLoadData() {
        String result = data.loadData(FILE_PATH);
        assertEquals("Import erfolgreich abgeschlossen!\nAnzahl Fahrzeuge: 25\nAnzahl Fahrer: 50\nAnzahl Fahrten: 20000\n", result);
    }

    @Test
    void testLoadDataEmptyFile() {
        String result = data.loadData("src/tests/empty.db");
        assertEquals("Import erfolgreich abgeschlossen!\nAnzahl Fahrzeuge: 0\nAnzahl Fahrer: 0\nAnzahl Fahrten: 0\n", result);
    }

    @Test
    void testAddDriver() {
        // Need to think about this one, since I have the adder methods private
    }
}
