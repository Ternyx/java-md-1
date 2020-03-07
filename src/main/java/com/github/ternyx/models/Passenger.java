package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;

/**
 * Passenger
 */
public class Passenger extends Person {
    private boolean isAdult;
    private String extraNeeds;

    public Passenger(String name, String surname, IdenType idenType, Nationality nationality,
            String idenNr, boolean isAdult, String extraNeeds) {
        super(name, surname, idenType, nationality, idenNr);
        this.isAdult = isAdult;
        this.extraNeeds = Objects.requireNonNull(extraNeeds);
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean isAdult) {
        this.isAdult = isAdult;
    }

    public String getExtraNeeds() {
        return extraNeeds;
    }

    public void setExtraNeeds(String extraNeeds) {
        this.extraNeeds = Objects.requireNonNull(extraNeeds);
    }

    @Override
    public String toString() {
        return "Passenger [extraNeeds=" + extraNeeds + ", isAdult=" + isAdult + "]";
    }
}
