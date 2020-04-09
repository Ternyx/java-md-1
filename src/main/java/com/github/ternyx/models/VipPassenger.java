package com.github.ternyx.models;

import java.util.Objects;
import com.github.ternyx.enums.IdenType;
import com.github.ternyx.enums.Nationality;
import com.github.ternyx.ifaces.IdNumberGenerator;

/**
 * VipPassenger
 */
public class VipPassenger extends Passenger implements IdNumberGenerator {
    public static final String DEFAULT_NEEDS = "VIP";

    private static int vipNrCounter = 0;
    private int vipNr;
    private String loungeCardNr;

    public VipPassenger(String name, String surname, Nationality nationality, IdenType idenType, 
            String idenNr, boolean isAdult, String extraNeeds, String loungeCardNr) {
        super(name, surname, nationality, idenType, idenNr, isAdult, extraNeeds);

        generateNr();
        this.loungeCardNr = validateLoungeCardNr(loungeCardNr);
    }

    public int getVipNr() {
        return vipNr;
    }

    public String getLoungeCardNr() {
        return loungeCardNr;
    }

    public void setLoungeCardNr(String loungeCardNr) {
        this.loungeCardNr = validateLoungeCardNr(loungeCardNr);
    }

    private static String validateLoungeCardNr(String loungeCardNr) {
        if (loungeCardNr == null) {
            throw new NullPointerException("LoungeCardNr can't be null");
        } else if (loungeCardNr.isBlank()) {
            throw new IllegalArgumentException("LoungeCardNr can't be blank");
        }
        return loungeCardNr;
    }

    @Override
    public void generateExtraNeeds() {
        this.setExtraNeeds(DEFAULT_NEEDS);
    }

    @Override
    public void generateNr() {
        this.vipNr = vipNrCounter;
        ++vipNrCounter;
    }

    @Override
    public String toString() {
        return "VipPassenger [loungeCardNr=" + loungeCardNr + ", vipNr=" + vipNr + ", INHERITED ["
                + super.toString() + "]]";
    }

}
