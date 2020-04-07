package com.github.ternyx.services;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import com.github.ternyx.enums.AirportName;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.models.Airport;
import com.github.ternyx.models.BoardingPass;
import com.github.ternyx.models.Flight;
import com.github.ternyx.models.Passenger;
import com.github.ternyx.models.VipPassenger;
import com.github.ternyx.utils.FlightKey;

/**
 * AirportService
 */
public class AirportService {
    private static Map<String, Passenger> allPassengers = new HashMap<>();
    private static Map<String, BoardingPass> allBoardingPasses = new HashMap<>();
    private static Map<AirportName, Airport> allAirports = new HashMap<>();
    private static Map<FlightKey, Flight> allFlights = new HashMap<>();

    public AirportService() { }

    public static Map<String, Passenger> getAllPassengers() {
        return allPassengers;
    }

    public static Map<String, BoardingPass> getAllBoardingPasses() {
        return allBoardingPasses;
    }

    public static Map<AirportName, Airport> getAllAirports() {
        return allAirports;
    }

    public static Map<FlightKey, Flight> getAllFlights() {
        return allFlights;
    }

    public String addNewPassenger(String name, String surname, Nationality nationality,
            IdenType idenType, String idenNr, boolean isAdult, String extraNeeds) {
        try {
            if (allPassengers.containsKey(idenNr)) {
                return null;
            }

            Passenger newPassenger = new Passenger(name, surname, nationality, idenType, idenNr,
                    isAdult, extraNeeds);

            allPassengers.put(newPassenger.getIdenNr(), newPassenger);
            return idenNr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addNewVipPassenger(String name, String surname, Nationality nationality,
            IdenType idenType, String idenNr, boolean isAdult, String extraNeeds,
            String loungeCardNr) {
        try {
            if (allPassengers.containsKey(idenNr)) {
                return null;
            }

            VipPassenger newVipPassenger = new VipPassenger(name, surname, nationality, idenType,
                    idenNr, isAdult, extraNeeds, loungeCardNr);

            allPassengers.put(newVipPassenger.getIdenNr(), (Passenger) newVipPassenger);
            return idenNr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AirportName addNewAirport(AirportName name, int capacity) {
        try {
            if (allAirports.containsKey(name)) {
                return null;
            }
            Airport newAirport = new Airport(name, capacity);
            allAirports.put(name, newAirport);

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addNewBoardingPassByObjects(Flight flight, Passenger passenger) {
        try {
            if (!allFlights.containsKey(FlightKey.getFlightKey(flight))
                    || !allPassengers.containsKey(passenger.getIdenNr())) {
                return null;
            }

            if (verifyIfPassengerInFlightDuringDate(passenger, flight)) {
                return null;
            }

            BoardingPass targetBoardingPass = new BoardingPass(passenger);
            String boardingPassNr = targetBoardingPass.getNr();

            allBoardingPasses.put(boardingPassNr, targetBoardingPass);
            flight.addNewBoardingPass(targetBoardingPass);

            return boardingPassNr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String addNewBoardingPassByNr(AirportName name, int flightNumber, String passengerId) {
        try {
            Passenger targetPassenger = allPassengers.get(passengerId);
            Flight targetFlight = allFlights.get(new FlightKey(name, flightNumber));

            if (targetPassenger == null || targetFlight == null) {
                return null;
            }

            if (verifyIfPassengerInFlightDuringDate(targetPassenger, targetFlight)) {
                return null;
            }

            BoardingPass targetBoardingPass = new BoardingPass(targetPassenger);
            String boardingPassNr = targetBoardingPass.getNr();

            allBoardingPasses.put(boardingPassNr, targetBoardingPass);
            targetFlight.addNewBoardingPass(targetBoardingPass);

            return boardingPassNr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FlightKey addNewFlight(AirportName airportFromName, AirportName airportToName, Date dateAndTime,
            byte duration) {
        try {
            Airport airportFrom = allAirports.get(airportFromName);
            Airport airportTo = allAirports.get(airportToName);

            if (airportFrom == null || airportTo == null) {
                return null;
            }

            Flight newFlight = new Flight(airportFrom, airportTo, dateAndTime, duration);
            FlightKey key = FlightKey.getFlightKey(newFlight);

            allFlights.put(key, newFlight);
            return key;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // if there are any collisions, all found passengers will be deleted
    public boolean deletePassengerByNameAndSurname(String name, String surname) {
        Set<String> passengerIds = allPassengers.values().stream()
                .filter(p -> p.getName().equals(name) && p.getSurname().equals(surname))
                .map(Passenger::getIdenNr)
                .collect(Collectors.toSet());

        return deletePassengers(passengerIds);
    }

    public boolean deletePassengerByIdenNr(String idenNr) {
        if (!allPassengers.containsKey(idenNr)) {
            return false;
        }

        return deletePassengers(new HashSet<String>(Arrays.asList(idenNr)));
    }

    // if there are any collisions, all found passengers will be deleted
    public boolean deleteVipPassengerVipNr(int vipIdenNr) {
        Set<String> passengerIds = allPassengers.values().stream()
                .filter(p -> (p instanceof VipPassenger) 
                    && ((VipPassenger) p).getVipNr() == vipIdenNr)
                .map(Passenger::getIdenNr)
                .collect(Collectors.toSet());

        return deletePassengers(passengerIds);
    }

    public boolean deleteAirportByNr(String airportNr) {
        Set<AirportName> targetAirports = allAirports.values().stream()
            .filter(a -> a.getAirportNr().equals(airportNr))
            .map(Airport::getName)
            .collect(Collectors.toSet());

        return deleteAirports(targetAirports);
    }

    public boolean deleteAirportByName(AirportName name) {
        if (!allAirports.containsKey(name)) {
            return false;
        }
        return deleteAirports(new HashSet<>(Arrays.asList(name)));
    }

    public boolean deleteBoardingPassByBoardingPassNr(String boardingPassNr) {
        if (!allBoardingPasses.containsKey(boardingPassNr)) {
            return false;
        }

        return deleteBoardingPasses(new HashSet<String>(Arrays.asList(boardingPassNr)));
    }

    public boolean deleteFlightInAirportByNr(AirportName airportName, int flightNr) {
        Flight targetFlight = allFlights.get(new FlightKey(airportName, flightNr));
        if (targetFlight == null) {
            return false;
        }

        return deleteFlights(new HashSet<>(Arrays.asList(targetFlight)));
    }

    // alias for addNewBoardingPassByNr
    public String addRegularPassengerInFlight(AirportName name, int flightNumber, String passengerId) {
        return addNewBoardingPassByNr(name, flightNumber, passengerId);
    }

    // alias for addNewBoardingPassByNr, but only with VipPassenger instanceof check
    
    public String addVipPassengerInFlight(AirportName name, int flightNumber, String passengerId) {
        Passenger targetPassenger = allPassengers.get(passengerId);
        if (targetPassenger != null && targetPassenger instanceof VipPassenger) {
            return addNewBoardingPassByNr(name, flightNumber, passengerId);
        }
        return null;
    }

    private void showAllPassengersHelper(AirportName airportName, int flightNr,
            Predicate<BoardingPass> predicate) {
        Flight targetFlight = allFlights.get(new FlightKey(airportName, flightNr));

        if (targetFlight == null) {
            return;
        }

        PriorityQueue<BoardingPass> boardingPasses = targetFlight.getAllPassengers();
        boardingPasses.stream()
            .filter(predicate)
            .forEach(System.out::println);
    }

    public void showAllPassengersInFlight(AirportName airportName, int flightNr) {
        showAllPassengersHelper(airportName, flightNr, b -> true);
    };

    public void showOnlyVipPassengersInFlight(AirportName airportName, int flightNr) {
        showAllPassengersHelper(airportName, flightNr,
                bP -> (bP.getPassenger()) instanceof VipPassenger);
    }

    public void showAllFlightsFromAirport(AirportName airportName) {
        allFlights.entrySet().stream()
            .filter(e -> e.getKey().getAirportName().equals(airportName))
            .forEach(e -> System.out.println(e.getValue()));
    }

    public void sortAllFlightsByDay() {
        allFlights.values().stream()
                .sorted((f1, f2) -> f1.getDateAndTime().compareTo(f2.getDateAndTime()))
                .forEach(System.out::println);
    }

    public void showAllArrivalsByTimeInAirport(AirportName airportName) {
        allFlights.values().stream()
            .filter(f -> f.getAirportTo().getName().equals(airportName))
            .map(AirportService::getFlightArrivalDate)
            .sorted((f1, f2) -> f1.getKey().compareTo(f2.getKey()))
            .map(Map.Entry::getValue)
            .forEach(System.out::println);
    }

    public void sortAllAirportsByCapacity() {
        allAirports.values().stream()
            .sorted((a1, a2) -> a1.getCapacity() - a2.getCapacity())
            .forEach(System.out::println);
    }

    private static Map.Entry<Date, Flight> getFlightArrivalDate(Flight targetFlight) {
        long durationMs = hoursToMs(targetFlight.getDuration());
        long targetFlightStart = targetFlight.getDateAndTime().getTime();
        return Map.entry(new Date(targetFlightStart + durationMs), targetFlight);
    }


    /*
     * Deleting a passenger will result in: 1) Deletion of that passenger 2) Deletion of boarding
     * passes that depend on that passenger 2.1) Removal of boarding pasess from the flight boarding
     * pass queues
     */

    private boolean deletePassengers(Set<String> passengerIds) {
        if (passengerIds.isEmpty()) {
            return false;
        }

        passengerIds.forEach(pid -> allPassengers.remove(pid));
        /*
         * allPassengers = allPassengers.entrySet().stream()
         *      .filter(x -> !passengerIds.contains(x.getKey()))
         *      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
         */


        Set<String> boardingPassIdsToDelete = allBoardingPasses.values().stream()
                .filter(bPass -> passengerIds.contains(bPass.getPassenger().getIdenNr()))
                .map(BoardingPass::getNr).collect(Collectors.toSet());

        deleteBoardingPasses(boardingPassIdsToDelete);
        return true;
    }

    /*
     * Deleting and airport will result in: 1) Deletion of that airport 2) Deletion of all flights
     * that depend on that airport 2.1) Deletion of all boardingPasses that depend on the respective
     * flight
     */

    private boolean deleteAirports(Set<AirportName> airportNames) {
        if (airportNames.isEmpty()) {
            return false;
        }

        // Remove airports
        airportNames.forEach(aId -> allAirports.remove(aId));
        /*
         * allAirports = allAirports.entrySet().stream()
         *      .filter(x -> !airportIds.contains(x.getKey()))
         *      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
         */

        Set<Flight> flightsToRemove = allFlights.values().stream()
                .filter(f -> airportNames.contains(f.getAirportFrom().getName())
                        || airportNames.contains(f.getAirportTo().getName()))
                .collect(Collectors.toSet());

        deleteFlights(flightsToRemove);
        return true;
    }

    /*
     * Deleting a boarding pass will result in: 1) Deletion of that boarding pass 2) Deletion of the
     * respective boarding pass in the flight boarding pass queue
     */
    private boolean deleteBoardingPasses(Set<String> boardingPassIds) {
        if (boardingPassIds.isEmpty()) {
            return false;
        }

        boardingPassIds.forEach(bPass -> allBoardingPasses.remove(bPass));

        allFlights.values().stream()
            .map(Flight::getAllPassengers)
            .forEach(p -> p.removeIf(bp -> boardingPassIds.contains(bp.getNr())));

        return true;
    }

    /*
     * Deleting a flight will result in: 1) Deletion of the flight from the collection 2) Deletion
     * of all boarding passes contained within that flight
     */
    private boolean deleteFlights(Set<Flight> flightsToDelete) {
        if (flightsToDelete.isEmpty()) {
            return false;
        }

        flightsToDelete.stream()
                .map(FlightKey::getFlightKey)
                .forEach(fKey -> allFlights.remove(fKey));

        // Remove boarding passes
        flightsToDelete.stream().flatMap(f -> f.getAllPassengers().stream())
                .map(BoardingPass::getNr)
                // .distinct
                .forEach(bId -> allBoardingPasses.remove(bId));
        return true;
    }

    private boolean verifyIfPassengerInFlightDuringDate(Passenger p, Flight targetFlight) {
        if (p == null || targetFlight == null) {
            throw new NullPointerException("Can't verify null passenger or targetFlight");
        }
        Date startWhen = targetFlight.getDateAndTime();
        Date endWhen = new Date(startWhen.getTime() + hoursToMs(targetFlight.getDuration()));
        String targetPassengerId = p.getIdenNr();

        for (Flight flight : allFlights.values()) {
            Date flightStart = flight.getDateAndTime();

            long targetEndInMs = hoursToMs(flight.getDuration());
            Date flightEnd = new Date(flightStart.getTime() + targetEndInMs);

            boolean isDateBetween = checkIfDateBetween(startWhen, flightStart, flightEnd)
                    || checkIfDateBetween(endWhen, flightStart, flightEnd);

            if (!isDateBetween) {
                continue;
            }

            for (BoardingPass bPass : flight.getAllPassengers()) {
                if (bPass.getPassenger().getIdenNr().equals(targetPassengerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    // start/end inclusive
    private static boolean checkIfDateBetween(Date targetDate, Date leftDate, Date rightDate) {
        return leftDate.compareTo(targetDate) <= 0 && rightDate.compareTo(targetDate) >= 0;
    }

    private static int hoursToMs(int hours) {
        return 1000 * 60 * 60 * hours;
    }
}
