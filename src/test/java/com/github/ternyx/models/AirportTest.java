package com.github.ternyx.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.github.ternyx.enums.AirportName;
import org.junit.jupiter.api.Test;

/**
 * AirportTest
 */
public class AirportTest {

    @Test
    public void Airport_NullParameters_ExceptionThrown() {
        assertThrows(NullPointerException.class, () -> new Airport(null, 0));
    }

    @Test
    public void Airport_NegativeCapacity_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new Airport(AirportName.RIGA, -1));
    }

    @Test 
    public void Airport_ValidCapacity_ReturnsInput() {
        assertEquals(100, new Airport(AirportName.RIGA, 100).getCapacity());
    }
}
