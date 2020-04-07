package com.github.ternyx.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Calendar;
import java.util.Date;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.models.Airport;
import com.github.ternyx.models.Flight;
import com.github.ternyx.models.Passenger;
import com.github.ternyx.models.VipPassenger;
import com.github.ternyx.utils.FlightKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * AirportServiceTest
 */
public class AirportServiceTest {
    private final AirportService service = new AirportService();

    // Passenger
    private static final String name = "Name";
    private static final String surname = "Surname";
    private static final Nationality nationality = Nationality.LATVIAN;
    private static final IdenType idenType = IdenType.IDCARD;
    private static final String idenNr = "1";
    private static final boolean isAdult = true;
    private static final String extraNeeds = "water";
    // VipPassenger
    private static final String loungeCardNr = "1";

    // Flight
    private static final AirportName airportFrom = AirportName.RIGA;
    private static final AirportName airportTo = AirportName.OLSO;
    private static Date dateAndTime;
    private static final byte duration = 1;

    @BeforeAll
    public static void initializeDate() {
        Calendar t = Calendar.getInstance();
        t.add(Calendar.HOUR, 1);
        dateAndTime = t.getTime();
    }

    @AfterEach
    public void cleanContainers() {
        AirportService.getAllPassengers().clear();
        AirportService.getAllBoardingPasses().clear();
        AirportService.getAllAirports().clear();
        AirportService.getAllFlights().clear();
    }


    @Test
    public void AddNewPassenger_ValidPassenger_ValidPassengerFields() {
        String passengerNr = service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        assertNotEquals(null, passengerNr, "Passenger should have been added, null key returned");

        if (passengerNr == null) {
            return;
        }

        Passenger addedPassenger = AirportService.getAllPassengers().get(passengerNr);
        assertEquals(name, addedPassenger.getName());
        assertEquals(surname, addedPassenger.getSurname());
        assertEquals(nationality, addedPassenger.getNationality());
        assertEquals(idenType, addedPassenger.getIdenType());
        assertEquals(idenNr, addedPassenger.getIdenNr());
        assertEquals(isAdult, addedPassenger.isAdult());
        assertEquals(extraNeeds, addedPassenger.getExtraNeeds());
    }
    
    @Test
    public void AddNewPassenger_PassengerSameId_ValidContainerSize() {
        service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        assertEquals(1, AirportService.getAllPassengers().size(), "Airport size should be 1");
        service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        assertEquals(1, AirportService.getAllPassengers().size(),
                "Two passengers with same id added, allPassenger container size should be 2");
    }

    @Test 
    public void AddNewPassenger_DifferentPassengers_ValidContainerSize() {
        service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        assertEquals(1, AirportService.getAllPassengers().size());
        service.addNewPassenger(name, surname, nationality, idenType, "2", isAdult, extraNeeds);
        assertEquals(2, AirportService.getAllPassengers().size());
    }

    @Test
    public void AddNewVipPassenger_ValidVipPassenger_ValidPassengerFields() {
        String passengerNr = service.addNewVipPassenger(name, surname, nationality, idenType,
                idenNr, isAdult, extraNeeds, loungeCardNr);

        assertNotEquals(null, passengerNr, "Passenger should have been added, null key returned");

        if (passengerNr == null) {
            return;
        }

        VipPassenger addedPassenger = (VipPassenger) AirportService.getAllPassengers().get(passengerNr);
        assertEquals(name, addedPassenger.getName());
        assertEquals(surname, addedPassenger.getSurname());
        assertEquals(nationality, addedPassenger.getNationality());
        assertEquals(idenType, addedPassenger.getIdenType());
        assertEquals(idenNr, addedPassenger.getIdenNr());
        assertEquals(isAdult, addedPassenger.isAdult());
        assertEquals(extraNeeds, addedPassenger.getExtraNeeds());
        assertEquals(loungeCardNr, addedPassenger.getLoungeCardNr());
    }

    @Test
    public void AddNewAirport_AirportSameIds_ValidContainerSize() {
        service.addNewAirport(AirportName.RIGA, 10);
        assertEquals(1, AirportService.getAllAirports().size());
        service.addNewAirport(AirportName.RIGA, 10);
        assertEquals(1, AirportService.getAllAirports().size(), "Two airports with the same name added, size should be 1");
    }


    @Test
    public void AddNewAirport_DifferentAirports_ValidContainerSize() {
        service.addNewAirport(AirportName.OLSO, 10);
        assertEquals(1, AirportService.getAllAirports().size());
        service.addNewAirport(AirportName.RIGA, 10);
        assertEquals(2, AirportService.getAllAirports().size(), "Two different airports added, size should be 2");
    }

    @Test
    public void AddNewAirport_InvalidParameters_ExceptionThrown() {
        service.addNewAirport(AirportName.RIGA, -1);
        assertEquals(0, AirportService.getAllBoardingPasses().size(), "Negative capacity not permitted, size should be 0");
        service.addNewAirport(null, 1);
        assertEquals(0, AirportService.getAllBoardingPasses().size(), "Negative AirportName is not permitted, size should be 0");
    }

    @Test
    public void AddNewBoardingPassByObjects_NullParameters_ReturnsNullAndContainerSize0() {
        String key = service.addNewBoardingPassByObjects(null, null);
        assertEquals(null, key, "Boarding pass shouldn't have been added with null params");
        assertEquals(0, AirportService.getAllBoardingPasses().size(), "Boarding size should be 0 with invalid parameters");
    }

    @Test
    public void AddNewBoardingPassByObjects_ParametersNotAddedToContainer_ContainerSize0() {
        Airport airportFrom = new Airport(AirportName.RIGA, 50);
        Airport airportTo = new Airport(AirportName.TALLINN, 50);

        Flight nonAddedFlight = new Flight(airportFrom, airportTo, dateAndTime, duration);
        Passenger nonAddedPassenger = new Passenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        String key = service.addNewBoardingPassByObjects(nonAddedFlight, nonAddedPassenger);

        assertNull(key);
        assertEquals(0, AirportService.getAllBoardingPasses().size());
    }

    @Test
    public void AddNewBoardingPassByObjects_ValidParameters_ValidContainerSize() {
        service.addNewAirport(AirportName.RIGA, 100);
        service.addNewAirport(AirportName.OLSO, 200);
        FlightKey flightKey = service.addNewFlight(AirportName.RIGA, AirportName.OLSO, dateAndTime, duration);
        String passengerKey = service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);

        Flight flight = AirportService.getAllFlights().get(flightKey);
        Passenger passenger = AirportService.getAllPassengers().get(passengerKey);
        service.addNewBoardingPassByObjects(flight, passenger);

        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(1, flight.getAllPassengers().size());
    }

    @Test
    public void AddNewBoardingPassByObjects_PassengerInMultipleFlightsDuringDate_AddsOne() {
        AirportName airportFromName1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airportToName1 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airportFromName2 = service.addNewAirport(AirportName.TALLINN, 100);
        AirportName airportToName2 = service.addNewAirport(AirportName.BARSELONA, 100);
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 3);

        FlightKey flight1 =
                service.addNewFlight(airportFromName1, airportToName1, cal.getTime(), (byte) 4);

        // 1 hour before
        cal.add(Calendar.HOUR, -1);
        FlightKey flight2 =
                service.addNewFlight(airportFromName2, airportToName2, cal.getTime(), (byte) 2);

        Passenger passenger = AirportService.getAllPassengers().get(service.addNewPassenger(name,
                surname, nationality, idenType, idenNr, isAdult, extraNeeds));

        String boardingPass1 = service
                .addNewBoardingPassByObjects(AirportService.getAllFlights().get(flight1), passenger);

        assertNotNull(boardingPass1, "Boarding pass should have been added");
        assertEquals(1, AirportService.getAllBoardingPasses().size(),
                "New boarding pass added, size should be 1");

        String boardingPass2 = service
                .addNewBoardingPassByObjects(AirportService.getAllFlights().get(flight2), passenger);

        assertNull(boardingPass2, "Passenger is already in a flight during date, boarding pass shouldn't be created");
        assertEquals(1, AirportService.getAllBoardingPasses().size(),
                "Passenger is in flight during date, allBoardingPasses size should remain 1");
    }


    @Test
    public void AddNewBoardingPassByNr_NullParameters_ReturnsNullAndContainerSize0() {
        String key = service.addNewBoardingPassByNr(null, 0, "1");
        assertNull(key, "Key with invalid airportName should be null");
        assertEquals(0, AirportService.getAllBoardingPasses().size());
    }

    @Test
    public void AddNewBoardingPassByNr_ParametersNotAddedToContainer_ContainerSize0() {
        String key = service.addNewBoardingPassByNr(AirportName.RIGA, 1, "1");

        assertNull(key);
        assertEquals(0, AirportService.getAllBoardingPasses().size());
    }

    @Test
    public void AddNewBoardingPassByNr_ValidParameters_ReturnsKeyAndContainerSize1() {
        service.addNewAirport(AirportName.RIGA, 100);
        service.addNewAirport(AirportName.OLSO, 200);

        FlightKey flightKey = service.addNewFlight(AirportName.RIGA, AirportName.OLSO, dateAndTime, duration);
        String passengerKey = service.addNewPassenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);

        String boardingPassNr = service.addNewBoardingPassByNr(flightKey.getAirportName(), flightKey.getFlightId(), passengerKey);
        assertNotNull(boardingPassNr);
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey).getAllPassengers().size());
    }
    
    @Test
    public void AddNewFlight_NullParameters_ReturnsNullKeyAndContainerSize0() {
        assertNull(service.addNewFlight(null, airportTo, dateAndTime, duration));
        assertNull(service.addNewFlight(airportFrom, null, dateAndTime, duration));
        assertNull(service.addNewFlight(airportFrom, airportTo, null, duration));
        // not added
        assertNull(service.addNewFlight(airportFrom, airportTo, dateAndTime, duration));
        assertEquals(0, AirportService.getAllFlights().size());
    }

    @Test
    public void addNewFlight_ValidParameters_ReturnsKeyAndContainerSize1() {
        AirportName airportNameFrom = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airportNameTo = service.addNewAirport(AirportName.OLSO, 200);

        FlightKey flightKey = service.addNewFlight(airportNameFrom, airportNameTo, dateAndTime, duration);
        assertNotNull(flightKey);
        assertEquals(1, AirportService.getAllFlights().size());
    }


    @Test
    public void DeletePassengersByNameAndSurname_PassengersWithIdenticalNames_DeletesAll() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger(name, surname, nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions (boarding pass nr is not unique enough)
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        
        // possible collisions (boarding pass nr is not unique enough)
        assertEquals(2, AirportService.getAllBoardingPasses().size(),
                "2 Boarding passes added, container size should be 2 (NOTE: possible \"false\" positives due to bad boarding pass nr generation)");

        boolean deleted = service.deletePassengerByNameAndSurname(name, surname);
        assertTrue(deleted);

        assertEquals(0, AirportService.getAllPassengers().size());
        assertEquals(0, AirportService.getAllBoardingPasses().size());
        assertEquals(0, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(0, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
    }

    @Test
    public void DeletePassengerByIdenNr_TwoPassengersWithSameName_DeletesOne() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger(name, surname, nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions (boarding pass nr is not unique enough)
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());

        // possible collisions (boarding pass nr is not unique enough)
        assertEquals(2, AirportService.getAllBoardingPasses().size(),
                "2 Boarding passes added, container size should be 2 (NOTE: possible \"false\" positives due to bad boarding pass nr generation)");

        boolean deleted = service.deletePassengerByIdenNr("1");

        assertTrue(deleted);

        assertEquals(1, AirportService.getAllPassengers().size());
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(0, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());

    }

    @Test
    public void DeleteVipPassengerVipNr_TwoPassengers_DeletesOne() {
        String personId1 = service.addNewVipPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds, "1");
        String personId2 = service.addNewVipPassenger("Jack", "Daniels", nationality, idenType, "2", isAdult, extraNeeds, "2");

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        assertEquals(2, AirportService.getAllBoardingPasses().size());

        int nr1 = ((VipPassenger) service.getAllPassengers().get(personId1)).getVipNr();
        int nr2 = ((VipPassenger) service.getAllPassengers().get(personId2)).getVipNr();

        boolean deleted = service.deleteVipPassengerVipNr(nr1);

        assertTrue(deleted);
        assertEquals(1, AirportService.getAllPassengers().size());
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(0, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
    }

    @Test
    public void DeleteAirportByNr_ValidParameters_DeletesOne() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger("Jack", "Daniels", nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        assertEquals(2, AirportService.getAllBoardingPasses().size());

        String airportNr = AirportService.getAllAirports().get(airport3).getAirportNr();

        boolean deleted = service.deleteAirportByNr(airportNr);

        assertTrue(deleted);
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(1, AirportService.getAllFlights().size());
        assertEquals(2, AirportService.getAllAirports().size());
    }

    @Test
    public void DeletAirportByName_ValidParameters_DeletesOne() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger("Jack", "Daniels", nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        assertEquals(2, AirportService.getAllBoardingPasses().size());

        boolean deleted = service.deleteAirportByName(airport2);

        assertTrue(deleted);
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(1, AirportService.getAllFlights().size());
        assertEquals(2, AirportService.getAllAirports().size());

    }

    @Test
    public void DeleteBoardingPassByBoardingPassNr_ValidParemeters_DeleteOne() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger("Jack", "Daniels", nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        assertEquals(2, AirportService.getAllBoardingPasses().size());

        boolean deleted = service.deleteBoardingPassByBoardingPassNr(boardingPass1);

        assertTrue(deleted);
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(0, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
    }

    @Test
    public void DeleteFlightInAirportByNr_ValidParameter_DeletesOne() {
        String personId1 = service.addNewPassenger(name, surname, nationality, idenType, "1", isAdult, extraNeeds);
        String personId2 = service.addNewPassenger("Jack", "Daniels", nationality, idenType, "2", isAdult, extraNeeds);

        AirportName airport1 = service.addNewAirport(AirportName.RIGA, 100);
        AirportName airport2 = service.addNewAirport(AirportName.OLSO, 100);
        AirportName airport3 = service.addNewAirport(AirportName.TALLINN, 100);

        FlightKey flightKey1 = service.addNewFlight(airport1, airport2, dateAndTime, duration);
        FlightKey flightKey2 = service.addNewFlight(airport1, airport3, dateAndTime, duration);

        // possible collisions
        String boardingPass1 = service.addNewBoardingPassByNr(flightKey1.getAirportName(),
                flightKey1.getFlightId(), personId1);

        String boardingPass2 = service.addNewBoardingPassByNr(flightKey2.getAirportName(),
                flightKey2.getFlightId(), personId2);

        // pre checks
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(2, AirportService.getAllFlights().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey1).getAllPassengers().size());
        assertEquals(1, AirportService.getAllFlights().get(flightKey2).getAllPassengers().size());
        assertEquals(3, AirportService.getAllAirports().size());
        assertEquals(2, AirportService.getAllBoardingPasses().size());

        boolean deleted = service.deleteFlightInAirportByNr(flightKey1.getAirportName(), flightKey1.getFlightId());

        assertTrue(deleted);
        assertEquals(2, AirportService.getAllPassengers().size());
        assertEquals(1, AirportService.getAllBoardingPasses().size());
        assertEquals(1, AirportService.getAllFlights().size());
        assertEquals(3, AirportService.getAllAirports().size());
    }
}
