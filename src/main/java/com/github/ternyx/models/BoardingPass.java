package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.ifaces.IdNumberGenerator;
import com.github.ternyx.utils.RandomNumber;

/**
 * BoardingPass
 */
public class BoardingPass implements IdNumberGenerator {
    private Passenger passenger;
    private Seat seat;
    private String boardingPassNr;
    private short group;
    private static final short MAX_SEAT = 100;

    public BoardingPass(Passenger passenger) {
        this.passenger = Objects.requireNonNull(passenger);
        generateSeatByPriority();
        generateGroupByPriority();
        generateNr();
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = Objects.requireNonNull(passenger);
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getNr() {
        return boardingPassNr;
    } 

    public short getGroup() {
        return group;
    }

    private void generateGroupByPriority() {
        if (passenger instanceof VipPassenger) {
            this.group = 1;
        } else {
            this.group = (short) RandomNumber.randInt(2, 5);
        }
    }

    private void generateSeatByPriority() {
        char row = (char) RandomNumber.randInt('A', 'F');
        short s;

        if (passenger instanceof VipPassenger) {
            s = (short) RandomNumber.randInt(1, 3);
        } else {
            s = (short) RandomNumber.randInt(4, MAX_SEAT);
        }

        this.seat = new Seat(row, s);
    }

    @Override
    public void generateNr() {
        this.boardingPassNr = passenger.getName().substring(0, 1)
            + passenger.getSurname().substring(0, 1)
            + seat.getSeat() 
            + seat.getRow()
            + group;
    }

    @Override
    public String toString() {
        return "BoardingPass [boardingPassNr=" + boardingPassNr + ", group=" + group
                + ", passenger=" + passenger + ", seat=" + seat + "]";
    }


}
