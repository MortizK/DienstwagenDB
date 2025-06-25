import model.Car;
import model.Driver;
import service.*;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String filePath = "src/Dienstwagen2025.db";

        DataService data = new DataService();
        String result = data.loadData(filePath);
        // System.out.println(result);

        if (args == null) {
            return;
        }

        for (String arg : args) {
            String[] parts = arg.replace("\"", "").split("=");

            switch (parts[0]) {
                case "--fahrersuche":
                    List<Driver> drivers = data.getDriversByString(parts[1]);
                    System.out.println(drivers);
                    break;
                case "--fahrzeugsuche":
                    List<Car> cars = data.getCarsByString(parts[1]);
                    System.out.println(cars);
                    break;
                case "--fahrerZeitpunkt":
                    String speeding = data.getDriverSpeeding(parts[1], ";");
                    System.out.println(speeding);
                    break;
                case "--fahrerDatum":
                    String driversOfDay = data.getDriversOfDay(parts[1], ";");
                    System.out.println(driversOfDay);
                    break;
                case "--help":
                    System.out.println("Possible commands");
                    System.out.println("--fahrersuche=\"Hoff\"");
                    System.out.println("--fahrzeugsuche=\"Passat\"");
                    System.out.println("--fahrerZeitpunkt=\"S-CD-4737;2024-01-01T14:00:00\"");
                    System.out.println("--fahrerDatum=\"F001;2024-01-01\"");
                    System.out.println("--help");
                    break;
                default:
                    System.out.println("Invalid command, use '--help' for help");
            }
        }
    }
}