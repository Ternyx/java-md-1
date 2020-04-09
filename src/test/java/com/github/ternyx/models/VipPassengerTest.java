package com.github.ternyx.models;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import org.junit.jupiter.api.Test;

/**
 * VipPassengerTest
 */
public class VipPassengerTest {
    private static final String name = "Name";
    private static final String surname = "Surname";
    private static final Nationality nationality = Nationality.LATVIAN;
    private static final IdenType idenType = IdenType.IDCARD;
    private static final String idenNr = "1";
    private static final boolean isAdult = true;
    private static final String extraNeeds = "water";
    private static final String loungeCardNr = "1";

    @Test
    public void VipPassenger_NullParameters_ExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new VipPassenger(null, surname, nationality,
                idenType, idenNr, isAdult, extraNeeds, loungeCardNr));
        assertThrows(NullPointerException.class, () -> new VipPassenger(name, null, nationality,
                idenType, idenNr, isAdult, extraNeeds, loungeCardNr));
        assertThrows(NullPointerException.class, () -> new VipPassenger(name, surname, null,
                idenType, idenNr, isAdult, extraNeeds, loungeCardNr));
        assertThrows(NullPointerException.class, () -> new VipPassenger(name, surname, nationality,
                null, idenNr, isAdult, extraNeeds, loungeCardNr));
        assertThrows(NullPointerException.class, () -> new VipPassenger(name, surname, nationality,
                idenType, null, isAdult, extraNeeds, loungeCardNr));
        assertThrows(NullPointerException.class, () -> new VipPassenger(name, surname, nationality,
                idenType, null, isAdult, extraNeeds, null));
    }

    @Test
    public void VipPassenger_EmptyOrNullParameterExtraNeeds_GeneratesDefaultNeeds() {
        assertEquals(VipPassenger.DEFAULT_NEEDS,
                new VipPassenger(name, surname, nationality, idenType, idenNr, isAdult, "", loungeCardNr)
                        .getExtraNeeds(),
                "Extra needs should be " + Passenger.DEFAULT_NEEDS);

        assertEquals(VipPassenger.DEFAULT_NEEDS,
                new VipPassenger(name, surname, nationality, idenType, idenNr, isAdult, null, loungeCardNr)
                        .getExtraNeeds(),
                "Extra needs should be " + Passenger.DEFAULT_NEEDS);
    }
    
    @Test
    public void VipPassenger_BlankLoungeCardId_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new VipPassenger(name, surname,
                nationality, idenType, idenNr, isAdult, extraNeeds, " "));
    }

}
