package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Date;
import enums.AirportName;
import enums.IdenType;
import enums.Nationality;
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

    @Test
    public void Passenger_NullParameters_ExceptionThrown() {
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
    public void Passenger_EmptyOrNullParameterExtraNeeds_GeneratesDefaultNeeds() {
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
    public void Passenger_ValidNeeds_ReturnsInput() {
        assertEquals(extraNeeds,
                new Passenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds)
                        .getExtraNeeds());
    }
}
