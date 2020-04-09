package com.github.ternyx.models;

import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;

/**
 * Passenger
 */
public class Passenger extends Person {
    public static final String DEFAULT_NEEDS = "no needs";

    private boolean isAdult;
    private String extraNeeds;

    public Passenger(String name, String surname, Nationality nationality, IdenType idenType,
            String idenNr, boolean isAdult, String extraNeeds) {
        super(name, surname, nationality, idenType, idenNr);

        this.isAdult = isAdult;
        validateAndCreateNeeds(extraNeeds);
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
        validateAndCreateNeeds(extraNeeds);
    }

    private void validateAndCreateNeeds(String extraNeeds) {
        if (extraNeeds == null || extraNeeds.isBlank()) {
            generateExtraNeeds();
        } else {
            this.extraNeeds = extraNeeds;
        }
    }

    public void generateExtraNeeds() {
        this.extraNeeds = DEFAULT_NEEDS;
    }

    @Override
    public String toString() {
        return "Passenger [extraNeeds=" + extraNeeds + ", isAdult=" + isAdult + ", INHERITED ["
                + super.toString() + "]]";
    }
}
