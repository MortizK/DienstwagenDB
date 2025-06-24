package model;

import util.modelUtil;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String numberPlate;

    public Car(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid car term, should in Form: 'X123, Brand, Model, NumberPlate'");
        }
        this.id = modelUtil.formatId(parts[0], true);
        this.brand = parts[1].trim();
        this.model = parts[2].trim();
        this.numberPlate = parts[3].trim();
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "@[id=" + id +
                ", brand=" + brand +
                ", model=" + model +
                ", numberPlate=" + numberPlate + "]";
    }

    public int getId() {
        return id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }
}
