package model;

import util.modelUtil;

/**
 * Class to represent a Driver.
 */
public class Driver {
    private int id;
    private String name;
    private String surname;
    private String driverLicenseClass;

    /**
     * Constructor for Driver class with a term in the form: 'F123, Name, Surname, DriverLicenseClass'
     * @param term in the form: 'F001, Sophie, Mueller, BE'
     * @param delimiter mostly ','
     * @throws IllegalArgumentException if the term is not in the correct form
     */
    public Driver(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid driver term, should in Form: 'F001, Sophie, Mueller, BE'");
        }
        this.id = modelUtil.formatId(parts[0], true);
        this.name = parts[1].trim();
        this.surname = parts[2].trim();
        this.driverLicenseClass = parts[3].trim();
    }

    /**
     * Returns a String representation of the Driver object.<br>
     * Of Form: 'model.Driver@[id=X123, name=Name, surname=Surname, driverLicenseClass=DriverLicenseClass]'
     * @return String
     */
    @Override
    public String toString() {
        return getClass().getName() +
                "@[id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", driverLicenseClass=" + driverLicenseClass + "]";
    }

    /**
     * Getter of the id attribute.
     * @return id of the driver.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter of the name attribute.
     * @return name of the driver.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the full name of the driver.
     * @return 'name + surname'.
     */
    public String getFullName() {
        return name + " " + surname;
    }
}
