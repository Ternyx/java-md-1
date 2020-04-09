package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * SeatTest
 */
public class SeatTest {
    
    @Test
    public void Seat_NegativeSeat_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> new Seat('a', (short) -1));
    }

    @Test
    public void Seat_NonNegativeSeat_ReturnsInput() {
        assertEquals(0, new Seat('a', (short) 0).getSeat());
    }
}
