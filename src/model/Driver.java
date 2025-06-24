package model;

import util.modelUtil;

public class Driver {
    private int id;
    private String name;
    private String surname;
    private String driverLicenseClass;

    public Driver(String term, String delimiter) {
        String[] parts = term.split(delimiter);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid driver term, should in Form: 'X123, Name, Surname, DriverLicenseClass'");
        }
        this.id = modelUtil.formatId(parts[0], true);
        this.name = parts[1].trim();
        this.surname = parts[2].trim();
        this.driverLicenseClass = parts[3].trim();
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "@[id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", driverLicenseClass=" + driverLicenseClass + "]";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
