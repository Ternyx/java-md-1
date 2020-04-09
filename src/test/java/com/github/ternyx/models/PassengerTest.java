package com.github.ternyx.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import org.junit.jupiter.api.Test;

/**
 * PassengerTest
 */
public class PassengerTest {
    private static final String name = "Name";
    private static final String surname = "Surname";
    private static final Nationality nationality = Nationality.LATVIAN;
    private static final IdenType idenType = IdenType.IDCARD;
    private static final String idenNr = "1";
    private static final boolean isAdult = true;
    private static final String extraNeeds = "water";

    private static final Airport airportFrom = new Airport(AirportName.RIGA, 100);
    private static final Airport airportTo = new Airport(AirportName.TALLINN, 100);
    private static final Date dateAndTime = new Date(2120, 4, 6);
    private static final byte duration = (byte) 2;

    @Test
    public void test_Passenger_NullParameters_ExceptionThrown() {
        assertThrows(NullPointerException.class,
                () -> new Passenger(null, surname, nationality, idenType, idenNr, isAdult, extraNeeds));
        assertThrows(NullPointerException.class,
                () -> new Passenger(name, null, nationality, idenType, idenNr, isAdult, extraNeeds));
        assertThrows(NullPointerException.class,
                () -> new Passenger(name, surname, null, idenType, idenNr, isAdult, extraNeeds));
        assertThrows(NullPointerException.class,
                () -> new Passenger(name, surname, nationality, null, idenNr, isAdult, extraNeeds));
        assertThrows(NullPointerException.class,
                () -> new Passenger(name, surname, nationality, idenType, null, isAdult, extraNeeds));
}


    @Test
    public void test_Passenger_EmptyOrNullParameterExtraNeeds_GeneratesDefaultNeeds() {
        assertEquals(Passenger.DEFAULT_NEEDS,
                new Passenger(name, surname, nationality, idenType, idenNr, isAdult, "")
                        .getExtraNeeds(),
                "Extra needs should be " + Passenger.DEFAULT_NEEDS);

        assertEquals(Passenger.DEFAULT_NEEDS,
                new Passenger(name, surname, nationality, idenType, idenNr, isAdult, null)
                        .getExtraNeeds(),
                "Extra needs should be " + Passenger.DEFAULT_NEEDS);
    }
    
    @Test
    public void test_Passenger_ValidNeeds_ReturnsInput() {
        assertEquals(extraNeeds, new Passenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds).getExtraNeeds());
    }
}
