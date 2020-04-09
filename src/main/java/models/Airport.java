package models;

import java.util.Objects;
import enums.AirportName;
import ifaces.IdNumberGenerator;
import utils.RandomNumber;

/**
 * Airport
 */
public class Airport implements IdNumberGenerator {
    private AirportName name;
    private int capacity;
    private String airportNr;
    private static int flightNr = 0;
    private static final int MAX_CAPACITY = 100;

    public Airport(AirportName name, int capacity) {
        this.name = Objects.requireNonNull(name);
        this.capacity = validateCapacity(capacity);
        generateNr();
    }

    public AirportName getName() {
        return name;
    }

    public void setName(AirportName name) {
        this.name = Objects.requireNonNull(name);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = validateCapacity(capacity);
    }

    public String getAirportNr() {
        return airportNr;
    }

    @Override
    public void generateNr() {
        String airportName = this.name.toString();
        int length = airportName.length();
        if (length > 3) {
            length = 3;
        }

        this.airportNr = this.name.toString().substring(0, length) 
            + String.valueOf(RandomNumber.randInt(10, 100));
    }

    public int generateFlightNr() {
        return flightNr++;
    }

    private int validateCapacity(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity must be non-negative");

        if (capacity > MAX_CAPACITY) 
            throw new IllegalArgumentException("Capacity must be >= 0 && <= 100");

        return capacity;
    }

    @Override
    public String toString() {
        return "Airport [airportNr=" + airportNr + ", capacity=" + capacity + ", name=" + name
                + "]";
    }
}
