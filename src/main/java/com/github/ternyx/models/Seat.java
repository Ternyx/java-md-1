package com.github.ternyx.models;

/**
 * Seat
 */
public class Seat {
    private char row;
    private short seat;

    public Seat(char row, short seat) {
        this.row = row;
        this.seat = validateSeat(seat);
    }

    public char getRow() {
        return row;
    }


    public void setRow(char row) {
        this.row = row;
    }


    public short getSeat() {
        return seat;
    }


    public void setSeat(short seat) {
        this.seat = validateSeat(seat);
    }

    private static short validateSeat(short seat) {
        if (seat < 0)
            throw new IllegalArgumentException("Seat number must be non-negative");
        return seat;
    }

    @Override
    public String toString() {
        return "Seat [row=" + row + ", seat=" + seat + "]";
    }
}
