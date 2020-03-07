package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.utils.Names;

/**
 * Person
 */
public class Person {
    private String name;
    private String surname;
    private IdenType idenType;
    private Nationality nationality;
    private String idenNr;

    public Person(String name, String surname, IdenType idenType, Nationality nationality,
            String idenNr) {
        this.name = Names.verifyName(name);
        this.surname = Names.verifySurname(surname);
        this.idenType = Objects.requireNonNull(idenType);
        this.nationality = Objects.requireNonNull(nationality);
        this.idenNr = Objects.requireNonNull(idenNr);
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
        this.idenNr = Objects.requireNonNull(idenNr);
    }

    @Override
    public String toString() {
        return "Person [idenNr=" + idenNr + ", idenType=" + idenType + ", name=" + name
                + ", nationality=" + nationality + ", surname=" + surname + "]";
    }
}
