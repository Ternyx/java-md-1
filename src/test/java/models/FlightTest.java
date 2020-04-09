package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date;
import enums.AirportName;
import enums.IdenType;
import enums.Nationality;
import org.junit.jupiter.api.Test;

/**
 * FlightTest
 */
public class FlightTest {

    private static final Airport airportFrom = new Airport(AirportName.RIGA, 100);
    private static final Airport airportTo = new Airport(AirportName.TALLINN, 100);
    private static final Date dateAndTime = new Date(2120, 4, 6);
    private static final byte duration = (byte) 2;

    @Test
    public void Flight_NullParameters_ExceptionThrown() {
        assertThrows(NullPointerException.class,
                () -> new Flight(null, airportTo, dateAndTime, duration));
        assertThrows(NullPointerException.class,
                () -> new Flight(airportFrom, null, dateAndTime, duration));
        assertThrows(NullPointerException.class,
                () -> new Flight(airportFrom, airportTo, null, duration));
    }

    @Test 
    public void Flight_NegativeDuration_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new Flight(airportFrom, airportTo, dateAndTime, (byte) -1));
    }

    @Test 
    public void Flight_PositiveDuration_ReturnsInput() {
        assertEquals(1, new Flight(airportFrom, airportTo, dateAndTime, (byte)1).getDuration());
    }

    @Test 
    public void Flight_GeneratedValues_ReturnsValid() {
        Flight f1 = new Flight(airportFrom, airportTo, dateAndTime, duration);
        Flight f2 = new Flight(airportFrom, airportTo, dateAndTime, duration);
        assertEquals(f1.getFlightNr() + 1, f2.getFlightNr());
    }

    @Test 
    public void Flight_BoardingPassQueuePrecedence_ValidPrecedence() {
        Passenger p1 = new Passenger("Name", "Surname", Nationality.ITALIAN, IdenType.IDCARD, "1",
                true, "");
        VipPassenger vp1 = new VipPassenger("Name", "Surname", Nationality.ITALIAN, IdenType.IDCARD,
                "2", true, "", "1");
        Flight f1 = new Flight(airportFrom, airportTo, dateAndTime, duration);
        f1.addNewPassengerByBoardingPass(p1);
        f1.addNewPassengerByBoardingPass((Passenger) vp1);
        assertTrue(f1.getAllPassengers().peek().getPassenger() == vp1);
    }
}
