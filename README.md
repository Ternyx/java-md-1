# Usage
```
$ mvn package exec:java -q
```

In case a more normalized, "eclipse like" folder/package structure is necessary `$ git checkout normalized`

# Restrictions

## Models

* Most model classes don't auto correct or fallback to default values

    For example, if a Person's name is not valid, an error will be thrown. 

    Additionally, default constructors have been omitted in classes like Person, Passenger and VipPassenger.

## AirportService

* Methods that add models to containers, return the unique identifier (or null, if the entity wasn't added) associated with the entity, instead of a boolean

* Only **one** airport can be made per AirportName

    This is primarily done to prevent Flight key collisions (since it is based on the originating airport and the auto incremented value in that airport). 

    Alternatively, you could use the airportNr as the first part of the key for a Flight, but in that case you would have to improve the airportNr generation algorithm to prevent collisions.

# Issues

* Boarding pass ID collisions 

    Collisions are bound to happen, assuming that there is a substantial amount of Passengers added with the same first character in their names and surnames. 

    The possibility that VIP passengers will collide is even higher, since they can only be seated in seats from 1 to 3.

