package com.github.ternyx.main;

import java.util.Calendar;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.models.Airport;
import com.github.ternyx.models.BoardingPass;
import com.github.ternyx.models.Flight;
import com.github.ternyx.models.Passenger;
import com.github.ternyx.models.VipPassenger;
import com.github.ternyx.services.AirportService;
import com.github.ternyx.utils.FlightKey;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        AirportService service = new AirportService();

        String p1 = service.addNewPassenger("John", "Baskin", Nationality.ITALIAN, IdenType.IDCARD, "1", true,
                "no needs");
        String p2 = service.addNewPassenger("Karl", "Norris", Nationality.ESTONIAN, IdenType.IDCARD, "2",
                true, "no needs");
        String p3 = service.addNewPassenger("Adam", "Haber", Nationality.LATVIAN, IdenType.PASSPORT, "3", false,
                "needs milk");

        String vp1 = service.addNewVipPassenger("Kate", "Corbusier", Nationality.ESTONIAN,
                IdenType.IDCARD, "4", true, "no needs", "1");
        String vp2 = service.addNewVipPassenger("Ahmad ", "Kallinger", Nationality.LATVIAN,
                IdenType.IDCARD, "5", true, "no needs", "2");
        String vp3 = service.addNewVipPassenger("Amanda", "Leon", Nationality.LATVIAN, IdenType.IDCARD,
                "6", true, "no needs", "3");

        AirportName a1 = service.addNewAirport(AirportName.RIGA, 2000);
        AirportName a2 = service.addNewAirport(AirportName.OLSO, 4000);
        AirportName a3 = service.addNewAirport(AirportName.BARSELONA, 5000);

        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.add(Calendar.MINUTE, 1);
        FlightKey f1 = service.addNewFlight(a1, a2, calendarInstance.getTime(), (byte)1);
        calendarInstance.add(Calendar.HOUR, 1);
        FlightKey f2 = service.addNewFlight(a2, a3, calendarInstance.getTime(), (byte)4);
        calendarInstance.add(Calendar.MONTH, 1);
        FlightKey f3 = service.addNewFlight(a3, a1, calendarInstance.getTime(), (byte)6);


        String bp1 = service.addNewBoardingPassByNr(f1.getAirportName(), f1.getFlightId(), p1);
        String bp2 = service.addNewBoardingPassByNr(f1.getAirportName(), f1.getFlightId(), p2);
        String bp3 = service.addNewBoardingPassByNr(f1.getAirportName(), f1.getFlightId(), vp1);

        String bp4 = service.addNewBoardingPassByNr(f2.getAirportName(), f2.getFlightId(), vp2);
        String bp5 = service.addNewBoardingPassByNr(f2.getAirportName(), f2.getFlightId(), p3);
        String bp6 = service.addNewBoardingPassByNr(f2.getAirportName(), f2.getFlightId(), vp2);

        String bp7 = service.addNewBoardingPassByNr(f3.getAirportName(), f3.getFlightId(), p2);
        String bp8 = service.addNewBoardingPassByNr(f3.getAirportName(), f3.getFlightId(), vp3);
        String bp9 = service.addNewBoardingPassByNr(f3.getAirportName(), f3.getFlightId(), vp1);

        service.showAllFlightsFromAirport(a1);
    }
}
