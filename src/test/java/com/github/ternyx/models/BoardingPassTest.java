package com.github.ternyx.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import org.junit.jupiter.api.Test;

/**
 * BoardingPassTest
 */
public class BoardingPassTest {
    private static final String name = "Name";
    private static final String surname = "Surname";
    private static final Nationality nationality = Nationality.LATVIAN;
    private static final IdenType idenType = IdenType.IDCARD;
    private static final String idenNr = "1";
    private static final boolean isAdult = true;
    private static final String extraNeeds = "water";

    @Test
    public void BoardingPass_NullParameters_ExceptionThrown() {
        assertThrows(NullPointerException.class,
                () -> new Passenger(null, null, null, null, null, false, null));
    }

    @Test
    public void BoardingPass_ValidPassenger_ReturnsInput() {
        Passenger passenger =
                new Passenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        VipPassenger vipPassenger = new VipPassenger(name, surname, nationality, idenType, idenNr, isAdult,
                extraNeeds, "1");
        assertEquals(passenger, new BoardingPass(passenger).getPassenger());
        assertEquals(vipPassenger, new BoardingPass(vipPassenger).getPassenger());
    }

    @Test
    public void BoardingPass_GeneratedValues_ReturnsValid() {
        Passenger passenger =
                new Passenger(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);
        VipPassenger vipPassenger = new VipPassenger(name, surname, nationality, idenType, idenNr,
                isAdult, extraNeeds, "1");

        BoardingPass boardingPass = new BoardingPass(passenger);
        assertTrue(boardingPass.getGroup() >= 2 && boardingPass.getGroup() <= 5);
        assertTrue('A' <= boardingPass.getSeat().getRow() && boardingPass.getSeat().getRow() <= 'F');
        assertTrue(boardingPass.getSeat().getSeat() > 3);

        BoardingPass boardingPassVip = new BoardingPass(vipPassenger);
        assertTrue(boardingPassVip.getGroup() == 1);
        assertTrue('A' <= boardingPassVip.getSeat().getRow() && boardingPassVip.getSeat().getRow() <= 'F');
        assertTrue(1 <= boardingPassVip.getSeat().getSeat() && boardingPassVip.getSeat().getSeat() <= 3);

    }

}
