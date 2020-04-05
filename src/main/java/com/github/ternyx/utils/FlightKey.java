package com.github.ternyx.utils;

import java.util.Objects;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.models.Flight;

/**
 * FlightKey
 */
public class FlightKey {
    private AirportName airportName;
    private int flightId;

    public FlightKey(AirportName name, int flightId) {
        this.airportName = Objects.requireNonNull(name);
        this.flightId = Objects.requireNonNull(flightId);
    }

    public AirportName getAirportName() {
        return airportName;
    }

    public void setAirportName(AirportName airportName) {
        this.airportName = Objects.requireNonNull(airportName);
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public static FlightKey getFlightKey(Flight targetFlight) {
        Objects.requireNonNull(targetFlight);
        return new FlightKey(targetFlight.getAirportFrom().getName(),
                targetFlight.getFlightNr());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof FlightKey)) {
            return false;
        }

        FlightKey key = (FlightKey) o;

        return this.flightId == key.flightId && this.airportName.equals(key.airportName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.airportName, this.flightId);
    }
}
