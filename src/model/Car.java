package model;

import util.modelUtil;

public class Car {
    private int id;
    private String brand;
    private String model;
    private String numberPlate;

    /**
     * Constructor for Car class with a term in the form: 'V123, Brand, Model, NumberPlate'
     * @param term in the form: 'V001, Opel, Corsa, S-BC-4566'
     * @param delimiter mostly ','
     * @throws IllegalArgumentException if the term is not in the correct form
     */
    public Car(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid car term, should in Form: 'V001, Opel, Corsa, S-BC-4566'");
        }
        this.id = modelUtil.formatId(parts[0], true);
        this.brand = parts[1].trim();
        this.model = parts[2].trim();
        this.numberPlate = parts[3].trim();
    }

    /**
     * Returns a String representation of the Car object.<br>
     * Of Form: 'model.Car@[id=X123, brand=Brand, model=Model, numberPlate=NumberPlate]'
     * @return String
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "@[id=" + id +
                ", brand=" + brand +
                ", model=" + model +
                ", numberPlate=" + numberPlate + "]";
    }

    /**
     * Getter of the id attribute.
     * @return id of the car.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of the model attribute.
     * @return model of the car.
     */
    public String getModel() {
        return model;
    }

    /**
     * Getter of the brand attribute.
     * @return brand of the car.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Getter of the numberPlate attribute.
     * @return numberPlate of the car.
     */
    public String getNumberPlate() {
        return numberPlate;
    }
}
