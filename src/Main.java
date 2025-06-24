import service.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String filePath = "src/Dienstwagen2025.db";

        DataService data = new DataService();
        String result = data.loadData(filePath);
        System.out.println(result);
    }
}