package models;

import java.util.Objects;
import enums.IdenType;
import enums.Nationality;
import utils.Names;

/**
 * Person
 */
public class Person {
    private String name;
    private String surname;
    private IdenType idenType;
    private Nationality nationality;
    private String idenNr;

    public Person(String name, String surname, Nationality nationality, IdenType idenType,
            String idenNr) {
        this.name = Names.verifyName(name);
        this.surname = Names.verifySurname(surname);
        this.nationality = Objects.requireNonNull(nationality);
        this.idenType = Objects.requireNonNull(idenType);
        this.idenNr = verifyIdenNr(idenNr);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Names.verifyName(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = Names.verifySurname(surname);
    }

    public IdenType getIdenType() {
        return idenType;
    }

    public void setIdenType(IdenType idenType) {
        this.idenType = Objects.requireNonNull(idenType);
    }

    public Nationality getNationality() {
        return nationality;
    }

    public void setNationality(Nationality nationality) {
        this.nationality = Objects.requireNonNull(nationality);
    }

    public String getIdenNr() {
        return idenNr;
    }

    public void setIdenNr(String idenNr) {
        this.idenNr = verifyIdenNr(idenNr);
    }

    private static String verifyIdenNr(String idenNr) {
        if (idenNr == null) {
            throw new NullPointerException("IdenNr can't be null");
        } else if (idenNr.isBlank()) {
            throw new IllegalArgumentException("IdenNr can't be blank");
        } 
        return idenNr;
    }

    @Override
    public String toString() {
        return "Person [idenNr=" + idenNr + ", idenType=" + idenType + ", name=" + name
                + ", nationality=" + nationality + ", surname=" + surname + "]";
    }
}
