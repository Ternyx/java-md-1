package com.github.ternyx.models;

import java.util.Date;
import java.util.Objects;
import java.util.PriorityQueue;
import com.github.ternyx.ifaces.IdNumberGenerator;

/**
 * Flight
 */
public class Flight implements IdNumberGenerator {
    private Airport airportFrom;
    private Airport airportTo;

    private int flightNr;

    private Date dateAndTime;
    private byte duration;
    private PriorityQueue<BoardingPass> allPassengers = new PriorityQueue<>();

    public Flight(Airport airportFrom, Airport airportTo, Date dateAndTime, byte duration) {
        this.airportFrom = Objects.requireNonNull(airportFrom);
        this.airportTo = Objects.requireNonNull(airportTo);
        this.dateAndTime = Objects.requireNonNull(dateAndTime);
        this.duration = validateDuration(duration);
        generateNr();
    }


    public Airport getAirportFrom() {
        return airportFrom;
    }


    public void setAirportFrom(Airport airportFrom) {
        this.airportFrom = Objects.requireNonNull(airportFrom);
    }


    public Airport getAirportTo() {
        return airportTo;
    }


    public void setAirportTo(Airport airportTo) {
        this.airportTo = Objects.requireNonNull(airportTo);
    }


    public int getFlightNr() {
        return flightNr;
    }


    public Date getDateAndTime() {
        return dateAndTime;
    }


    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = Objects.requireNonNull(dateAndTime);
    }


    public byte getDuration() {
        return duration;
    }


    public void setDuration(byte duration) {
        this.duration = validateDuration(duration);
    }


    public PriorityQueue<BoardingPass> getAllPassengers() {
        return allPassengers;
    }


    public void setAllPassengers(PriorityQueue<BoardingPass> allPassengers) {
        this.allPassengers = Objects.requireNonNull(allPassengers);
    }

    public boolean addNewPassengerByBoardingPass(Passenger passenger) {
        if (passenger == null) {
            return false;
        }

        allPassengers.add(new BoardingPass(passenger));
        return true;
    }

    public boolean addNewBoardingPass(BoardingPass boardingPass) {
        if (boardingPass == null) {
            return false;
        } 

        allPassengers.add(boardingPass);
        return true;
    }

    @Override
    public void generateNr() {
        this.flightNr = this.airportFrom.generateFlightNr();
    }

    private static byte validateDuration(byte duration) {
        if (duration < 0)
            throw new IllegalArgumentException("Duration must be non-negative");
        return duration;
    }


    @Override
    public String toString() {
        return "Flight [airportFrom=" + airportFrom + ", airportTo=" + airportTo
            + ", allPassengers=" + allPassengers + ", dateAndTime=" + dateAndTime
            + ", duration=" + duration + ", flightNr=" + flightNr + "]";
    }
}
