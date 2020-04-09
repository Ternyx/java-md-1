package models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import enums.IdenType;
import enums.Nationality;
import org.junit.jupiter.api.Test;

/**
 * PersonTest
 */
public class PersonTest {
    private static final String name = "Name";
    private static final String surname = "Surname";
    private static final Nationality nationality = Nationality.LATVIAN;
    private static final IdenType idenType = IdenType.IDCARD;
    private static final String idenNr = "1";

    @Test
    public void Person_NullParamenters_ExceptionThrown() {
        assertThrows(NullPointerException.class,
                () -> new Person(null, surname, nationality, idenType, idenNr));
        assertThrows(NullPointerException.class,
                () -> new Person(name, null, nationality, idenType, idenNr));
        assertThrows(NullPointerException.class,
                () -> new Person(name, surname, null, idenType, idenNr));
        assertThrows(NullPointerException.class,
                () -> new Person(name, surname, nationality, null, idenNr));
        assertThrows(NullPointerException.class,
                () -> new Person(name, surname, nationality, idenType, null));
    }

    @Test
    public void Person_BlankParameters_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person("", surname, nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "", nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, surname, nationality, idenType, ""));
    }

    @Test
    public void Person_InvalidName_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person("adam", surname, nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person("1Adam", surname, nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person("Adam$", surname, nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person("AdaM", surname, nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person("Adam anders", surname, nationality, idenType, idenNr));
    }

    @Test
    public void test_Person_ValidName_ReturnsInput() {
        String name1 = "Adam";
        String name2 = "Adam Ralph";

        assertEquals(name1, new Person(name1, surname, nationality, idenType, idenNr).getName(),
                "Name should equal " + name1);
        assertEquals(name2, new Person(name2, surname, nationality, idenType, idenNr).getName(),
                "Name should equal " + name2);
    }

    @Test
    public void Person_InvalidSurname_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "surname", nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "surnamE", nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "Surname Surname", nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "surname1", nationality, idenType, idenNr));
        assertThrows(IllegalArgumentException.class,
                () -> new Person(name, "surn$ame", nationality, idenType, idenNr));
    }

    @Test 
    public void Person_ValidSurname_ReturnsInput() {
        assertEquals(name, new Person(name, surname, nationality, idenType, idenNr).getName(),
                "Name should equal " + name);
    }
}
