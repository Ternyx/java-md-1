package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.ifaces.IdNumberGenerator;
import com.github.ternyx.utils.RandomNumber;

/**
 * Airport
 */
public class Airport implements IdNumberGenerator {
    private AirportName name;
    private int capacity;
    private String airportNr;

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
        this.airportNr = this.name.toString().substring(0, 4) 
            + String.valueOf(RandomNumber.randInt(10, 100));
    }

    private int validateCapacity(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity must be non-negative");

        return capacity;
    }

    @Override
    public String toString() {
        return "Airport [airportNr=" + airportNr + ", capacity=" + capacity + ", name=" + name
                + "]";
    }
}
